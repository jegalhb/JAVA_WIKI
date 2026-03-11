package Reproject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class ConceptRepository {
    private static final String DATA_FILE = "data.json";
    private static final String LEGACY_DATA_FILE = "data.txt";

    // 소스 인코딩 이슈를 피하기 위해 카테고리 한글은 유니코드 이스케이프로 관리한다.
    private static final String CAT_METHOD = "메소드";
    private static final String CAT_BASIC = "기초";
    private static final String CAT_MID = "중급";
    private static final String CAT_ADV = "고급";

    private final Map<String, Concept> database = new HashMap<>();

    public ConceptRepository() {
        initData();
        initMethod();

        // 1) 기본 저장 포맷은 JSON을 우선으로 사용한다.
        readJsonFile(DATA_FILE, database);

        // 2) JSON이 없고 기존 txt만 있으면 1회 마이그레이션을 수행한다.
        if (database.isEmpty()) {
            boolean migrated = readLegacyTextFile(LEGACY_DATA_FILE, database);
            if (migrated) {
                save();
            }
        }

        // 3) 저장 데이터가 전혀 없으면 기본 샘플 데이터를 채운다.
        if (database.isEmpty()) {
            seedDefaultConcepts();
            save();
        }
    }

    private void initData() {
        // 기존 구조와의 호환을 위해 유지한다.
    }

    private void initMethod() {
        // 기존 구조와의 호환을 위해 유지한다.
    }

    public synchronized void save() {
        writeJsonFile(DATA_FILE, database);
    }

    public synchronized List<Concept> findMethodAll() {
        return database.values().stream()
                .filter(c -> CAT_METHOD.equals(c.getCategory()))
                .sorted(Comparator.comparing(Concept::getTitle))
                .toList();
    }

    public synchronized void addConcept(Concept c) {
        database.put(c.getId(), c);
    }

    public synchronized void replaceAll(List<Concept> concepts) {
        database.clear();
        for (Concept c : concepts) {
            database.put(c.getId(), c);
        }
    }

    public synchronized void deleteConcept(String id) {
        database.remove(id);
    }

    public synchronized Concept findById(String id) {
        return database.get(id);
    }

    public synchronized List<Concept> findAll() {
        return new ArrayList<>(database.values());
    }

    /**
     * JSON 배열([ ... ]) 파일을 읽어 메모리 저장소(Map)로 반영한다.
     */
    private void readJsonFile(String filename, Map<String, Concept> db) {
        Path path = Paths.get(filename);
        if (!Files.exists(path)) return;

        try {
            String json = Files.readString(path, StandardCharsets.UTF_8);
            if (json == null || json.trim().isEmpty()) return;

            List<Concept> concepts = parseConceptArray(json);
            for (Concept c : concepts) {
                db.put(c.getId(), c);
            }
        } catch (Exception e) {
            System.err.println("JSON load failed: " + e.getMessage());
        }
    }

    /**
     * 레거시 txt 포맷을 JSON 체계로 옮기기 위한 마이그레이션 경로이다.
     * 형식: id/title/category/description.../---
     */
    private boolean readLegacyTextFile(String filename, Map<String, Concept> db) {
        File file = new File(filename);
        if (!file.exists()) return false;

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            List<String> block = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().equals("---")) {
                    parseLegacyBlockAndPut(block, db);
                    block.clear();
                    continue;
                }
                block.add(line);
            }
            parseLegacyBlockAndPut(block, db);

            return !db.isEmpty();
        } catch (IOException e) {
            System.err.println("Legacy txt load failed: " + e.getMessage());
            return false;
        }
    }

    private void parseLegacyBlockAndPut(List<String> block, Map<String, Concept> db) {
        if (block == null || block.isEmpty()) return;

        List<String> lines = new ArrayList<>();
        for (String raw : block) {
            if (raw != null && !raw.trim().isEmpty()) {
                lines.add(raw.trim());
            }
        }

        if (lines.size() < 3) return;

        String id = lines.get(0);
        String title = lines.get(1);
        String category = normalizeCategory(lines.get(2), id);
        if (id.isEmpty() || title.isEmpty() || category.isEmpty()) return;

        Concept concept = new Concept(id, title, category);
        for (int i = 3; i < lines.size(); i++) {
            concept.addLine(lines.get(i));
        }

        db.put(id, concept);
    }

    /**
     * JSON 저장 시 정렬 순서를 고정하고, 임시 파일 교체로 저장 안정성을 높인다.
     */
    private void writeJsonFile(String filename, Map<String, Concept> db) {
        List<Concept> concepts = new ArrayList<>(db.values());
        concepts.sort(Comparator.comparing(Concept::getId));

        StringBuilder json = new StringBuilder();
        json.append("[\n");

        for (int i = 0; i < concepts.size(); i++) {
            Concept c = concepts.get(i);
            json.append("  {\n");
            json.append("    \"id\": \"").append(escape(c.getId())).append("\",\n");
            json.append("    \"title\": \"").append(escape(c.getTitle())).append("\",\n");
            json.append("    \"category\": \"").append(escape(c.getCategory())).append("\",\n");
            json.append("    \"descriptionLines\": [\n");

            List<String> lines = c.getDescriptionLines();
            for (int j = 0; j < lines.size(); j++) {
                json.append("      \"").append(escape(lines.get(j))).append("\"");
                if (j < lines.size() - 1) json.append(",");
                json.append("\n");
            }

            json.append("    ]\n");
            json.append("  }");
            if (i < concepts.size() - 1) json.append(",");
            json.append("\n");
        }

        json.append("]\n");

        Path target = Paths.get(filename);
        Path temp = Paths.get(filename + ".tmp");

        try {
            Files.writeString(temp, json.toString(), StandardCharsets.UTF_8);
            try {
                Files.move(temp, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException ignore) {
                Files.move(temp, target, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            System.err.println("JSON save failed: " + e.getMessage());
        }
    }

    /**
     * JSON 문자열 특수문자를 안전하게 이스케이프 처리한다.
     */
    private String escape(String raw) {
        if (raw == null) return "";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < raw.length(); i++) {
            char ch = raw.charAt(i);
            switch (ch) {
                case '"': sb.append("\\\""); break;
                case '\\': sb.append("\\\\"); break;
                case '\b': sb.append("\\b"); break;
                case '\f': sb.append("\\f"); break;
                case '\n': sb.append("\\n"); break;
                case '\r': sb.append("\\r"); break;
                case '\t': sb.append("\\t"); break;
                default:
                    if (ch < 0x20) sb.append(String.format("\\u%04x", (int) ch));
                    else sb.append(ch);
            }
        }
        return sb.toString();
    }

    private List<Concept> parseConceptArray(String json) {
        JsonReader reader = new JsonReader(json);
        List<Concept> result = new ArrayList<>();

        reader.skipWhitespace();
        reader.expect('[');
        reader.skipWhitespace();

        if (reader.consumeIf(']')) return result;

        while (true) {
            Concept concept = readConceptObject(reader);
            if (concept != null) result.add(concept);

            reader.skipWhitespace();
            if (reader.consumeIf(',')) {
                reader.skipWhitespace();
                continue;
            }

            reader.expect(']');
            break;
        }

        reader.skipWhitespace();
        if (!reader.isEnd()) {
            throw new IllegalArgumentException("Unexpected trailing JSON content");
        }

        return result;
    }

    private Concept readConceptObject(JsonReader reader) {
        reader.skipWhitespace();
        reader.expect('{');
        reader.skipWhitespace();

        String id = "";
        String title = "";
        String category = "";
        List<String> descriptionLines = new ArrayList<>();

        if (reader.consumeIf('}')) return null;

        while (true) {
            String key = reader.readString();
            reader.skipWhitespace();
            reader.expect(':');
            reader.skipWhitespace();

            if ("id".equals(key)) {
                id = reader.readNullableString();
            } else if ("title".equals(key)) {
                title = reader.readNullableString();
            } else if ("category".equals(key)) {
                category = reader.readNullableString();
            } else if ("descriptionLines".equals(key)) {
                descriptionLines = readStringArray(reader);
            } else {
                reader.skipValue();
            }

            reader.skipWhitespace();
            if (reader.consumeIf(',')) {
                reader.skipWhitespace();
                continue;
            }

            reader.expect('}');
            break;
        }

        id = id == null ? "" : id.trim();
        title = title == null ? "" : title.trim();
        category = normalizeCategory(category, id);

        if (id.isEmpty() || title.isEmpty()) return null;

        Concept concept = new Concept(id, title, category);
        for (String line : descriptionLines) {
            if (line != null) concept.addLine(line);
        }

        return concept;
    }

    private List<String> readStringArray(JsonReader reader) {
        List<String> list = new ArrayList<>();
        reader.expect('[');
        reader.skipWhitespace();

        if (reader.consumeIf(']')) return list;

        while (true) {
            String value = reader.readNullableString();
            if (value != null) list.add(value);

            reader.skipWhitespace();
            if (reader.consumeIf(',')) {
                reader.skipWhitespace();
                continue;
            }

            reader.expect(']');
            break;
        }

        return list;
    }

    private String normalizeCategory(String category, String id) {
        String c = category == null ? "" : category.trim().toLowerCase(Locale.ROOT);

        if (c.contains("method") || c.contains("\uBA54\uC18C\uB4DC")) return CAT_METHOD;
        if (c.contains("basic") || c.contains("\uAE30\uCD08")) return CAT_BASIC;
        if (c.contains("intermediate") || c.contains("\uC911\uAE09")) return CAT_MID;
        if (c.contains("advanced") || c.contains("\uACE0\uAE09")) return CAT_ADV;

        return categoryById(id);
    }

    private String categoryById(String id) {
        if (id == null || id.isEmpty()) return CAT_METHOD;
        char p = Character.toUpperCase(id.charAt(0));
        if (p == 'B') return CAT_BASIC;
        if (p == 'I') return CAT_MID;
        if (p == 'A') return CAT_ADV;
        return CAT_METHOD;
    }

    private void seedDefaultConcepts() {
        addConcept(new Concept("M01", "System.out.println()", CAT_METHOD)
                .addLine("[DESC] Console output with newline.")
                .addLine("[CODE] System.out.println(\"Hello Java\");"));

        addConcept(new Concept("M02", "Scanner.nextLine()", CAT_METHOD)
                .addLine("[DESC] Read full line until Enter.")
                .addLine("[CODE] String str = sc.nextLine();"));
    }

    /**
     * 외부 라이브러리 없이 최소 JSON 문법만 처리하는 내부 리더.
     */
    private static class JsonReader {
        private final String src;
        private int idx;

        JsonReader(String src) {
            this.src = src == null ? "" : src;
            this.idx = 0;
        }

        boolean isEnd() {
            return idx >= src.length();
        }

        void skipWhitespace() {
            while (!isEnd()) {
                char ch = src.charAt(idx);
                if (Character.isWhitespace(ch)) idx++;
                else break;
            }
        }

        boolean consumeIf(char expected) {
            if (!isEnd() && src.charAt(idx) == expected) {
                idx++;
                return true;
            }
            return false;
        }

        void expect(char expected) {
            if (isEnd() || src.charAt(idx) != expected) {
                throw new IllegalArgumentException("JSON parse error: expected '" + expected + "' at idx=" + idx);
            }
            idx++;
        }

        String readNullableString() {
            skipWhitespace();
            if (matchLiteral("null")) {
                idx += 4;
                return null;
            }
            return readString();
        }

        String readString() {
            skipWhitespace();
            expect('"');

            StringBuilder sb = new StringBuilder();
            while (!isEnd()) {
                char ch = src.charAt(idx++);
                if (ch == '"') return sb.toString();

                if (ch == '\\') {
                    if (isEnd()) throw new IllegalArgumentException("Invalid JSON escape at end of input");
                    char esc = src.charAt(idx++);

                    switch (esc) {
                        case '"': sb.append('"'); break;
                        case '\\': sb.append('\\'); break;
                        case '/': sb.append('/'); break;
                        case 'b': sb.append('\b'); break;
                        case 'f': sb.append('\f'); break;
                        case 'n': sb.append('\n'); break;
                        case 'r': sb.append('\r'); break;
                        case 't': sb.append('\t'); break;
                        case 'u':
                            if (idx + 4 > src.length()) {
                                throw new IllegalArgumentException("Invalid unicode escape");
                            }
                            String hex = src.substring(idx, idx + 4);
                            idx += 4;
                            sb.append((char) Integer.parseInt(hex, 16));
                            break;
                        default:
                            throw new IllegalArgumentException("Unknown JSON escape: \\" + esc);
                    }
                } else {
                    sb.append(ch);
                }
            }

            throw new IllegalArgumentException("Unterminated JSON string");
        }

        void skipValue() {
            skipWhitespace();
            if (isEnd()) throw new IllegalArgumentException("Missing JSON value");

            char ch = src.charAt(idx);
            if (ch == '"') {
                readString();
                return;
            }

            if (ch == '{') {
                idx++;
                skipWhitespace();
                if (consumeIf('}')) return;

                while (true) {
                    readString();
                    skipWhitespace();
                    expect(':');
                    skipValue();
                    skipWhitespace();
                    if (consumeIf(',')) {
                        skipWhitespace();
                        continue;
                    }
                    expect('}');
                    return;
                }
            }

            if (ch == '[') {
                idx++;
                skipWhitespace();
                if (consumeIf(']')) return;

                while (true) {
                    skipValue();
                    skipWhitespace();
                    if (consumeIf(',')) {
                        skipWhitespace();
                        continue;
                    }
                    expect(']');
                    return;
                }
            }

            if (matchLiteral("true")) {
                idx += 4;
                return;
            }
            if (matchLiteral("false")) {
                idx += 5;
                return;
            }
            if (matchLiteral("null")) {
                idx += 4;
                return;
            }

            int start = idx;
            while (!isEnd()) {
                char c = src.charAt(idx);
                if ((c >= '0' && c <= '9') || c == '-' || c == '+' || c == '.' || c == 'e' || c == 'E') {
                    idx++;
                } else {
                    break;
                }
            }

            if (start == idx) {
                throw new IllegalArgumentException("Unknown JSON value at idx=" + idx);
            }
        }

        private boolean matchLiteral(String literal) {
            if (idx + literal.length() > src.length()) return false;
            return src.regionMatches(idx, literal, 0, literal.length());
        }
    }
}