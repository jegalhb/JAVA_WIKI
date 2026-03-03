package Reproject;

import java.util.*;
import java.util.stream.Collectors;

public class SearchService {
    private final ConceptRepository repository;

    public SearchService(ConceptRepository repository) {
        this.repository = repository;
    }


     //* 1. 일반 검색: 유사도 점수가 있는 항목들을 정렬하여 반환
    public List<Concept> search(String query) {
        if (query == null || query.trim().isEmpty()) {
            return repository.findAll();
        }

        String lowerQuery = query.toLowerCase().trim();

        return repository.findAll().stream()
                .map(concept -> new SearchResult(concept, calculateScore(concept, lowerQuery)))
                .filter(result -> result.score > 0)
                .sorted(Comparator.comparingDouble((SearchResult r) -> r.score).reversed())
                .map(result -> result.concept)
                .collect(Collectors.toList());
    }

    /**
     * 2. 추천 검색: 결과가 0개일 때 가장 점수가 높은 항목 하나를 추출
     */
    public Concept getBestMatch(String query) {
        if (query == null || query.trim().isEmpty()) return null;

        String lowerQuery = query.toLowerCase().trim();

        return repository.findAll().stream()
                .map(concept -> new SearchResult(concept, calculateScore(concept, lowerQuery)))
                .filter(result -> result.score > 0.5) // 최소한의 연관성 기준
                .max(Comparator.comparingDouble(r -> r.score))
                .map(r -> r.concept)
                .orElse(null);
    }

    /**
     * 3. 핵심 로직: 유사도 점수 계산 (오타 교정 포함)
     */
    private double calculateScore(Concept concept, String query) {
        double score = 0;
        String title = concept.getTitle().toLowerCase();

        // [A] 제목 포함 여부 체크
        if (title.contains(query)) {
            score += 10.0;
        } else {
            // [B] 단어 단위 분리 후 유사도 체크 (예: "클래쓰" -> "클래스" 매칭)
            String[] titleParts = title.split("\\s+");
            for (String part : titleParts) {
                double partSim = getSimilarityRatio(part, query);
                if (partSim > 0.5) {
                    score += partSim * 8.0;
                }
            }
        }

        // [C] 태그 일치 여부 체크
        for (String tag : concept.getTags()) {
            if (tag.toLowerCase().contains(query)) {
                score += 5.0;
            }
        }

        // [D] 전체 문장 유사도 보정
        double totalSim = getSimilarityRatio(title, query);
        score += totalSim * 3.0;

        return score;
    }

    /**
     * 4. 유사도 비율 산출 (0.0 ~ 1.0)
     */
    private double getSimilarityRatio(String s1, String s2) {
        if (s1 == null || s2 == null) return 0.0;
        if (s1.isEmpty() && s2.isEmpty()) return 1.0;
        if (s1.isEmpty() || s2.isEmpty()) return 0.0;

        int distance = computeLevenshteinDistance(s1.toLowerCase(), s2.toLowerCase());
        return 1.0 - ((double) distance / Math.max(s1.length(), s2.length()));
    }

    /**
     * 5. 레벤슈타인 거리 알고리즘 (편집 거리 계산)
     */
    private int computeLevenshteinDistance(String lhs, String rhs) {
        int[][] distance = new int[lhs.length() + 1][rhs.length() + 1];

        for (int i = 0; i <= lhs.length(); i++) distance[i][0] = i;
        for (int j = 1; j <= rhs.length(); j++) distance[0][j] = j;

        for (int i = 1; i <= lhs.length(); i++) {
            for (int j = 1; j <= rhs.length(); j++) {
                distance[i][j] = Math.min(
                        Math.min(distance[i - 1][j] + 1, distance[i][j - 1] + 1),
                        distance[i - 1][j - 1] + (lhs.charAt(i - 1) == rhs.charAt(j - 1) ? 0 : 1)
                );
            }
        }
        return distance[lhs.length()][rhs.length()];
    }

    // 결과 저장을 위한 내부 클래스
    private static class SearchResult {
        Concept concept;
        double score;
        SearchResult(Concept concept, double score) {
            this.concept = concept;
            this.score = score;
        }
    }
}