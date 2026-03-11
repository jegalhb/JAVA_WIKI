package Reproject;

/**
 * JAVA_WIKI 실행/동기화 흐름을 문자열로 정리한 안내 클래스.
 */
public final class JavaWikiProcessGuide {

    private JavaWikiProcessGuide() {
        // 유틸 클래스이므로 인스턴스 생성을 막는다.
    }

    public static String getProcessGuide() {
        String nl = System.lineSeparator();
        return String.join(nl,
                "================ JAVA_WIKI PROCESS GUIDE ================",
                "",
                "[A] Run Modes",
                "1) Offline: Main.main() -> Repository/SearchService/MainWikiFrame",
                "2) Online: WikiClient.main() -> build UI -> input IP/PORT -> client.start()",
                "",
                "[B] Core Components",
                "- UI: MainWikiFrame (search/list/detail/chat)",
                "- Edit UI: ConceptEditFrame (add/edit)",
                "- Repository: ConceptRepository (Map + data.json)",
                "- Search: SearchService (similarity/Levenshtein)",
                "- Network: WikiClient <-> WikiServer",
                "",
                "[C] Call Chains",
                "1) Search",
                "   ActionListener -> MainWikiFrame.performSearch()",
                "   -> SearchService.search(query) -> MainWikiFrame.updateList(results)",
                "",
                "2) Add/Edit",
                "   ConceptEditFrame.saveAction() -> MainWikiFrame.onDataAdded(concept)",
                "   -> ConceptRepository.addConcept(concept)",
                "   -> (online) WikiClient.send(\"ADD\", concept)",
                "   -> MainWikiFrame.refreshList()",
                "",
                "3) Delete",
                "   Delete button -> ConceptRepository.deleteConcept(id)",
                "   -> (online) WikiClient.send(\"DELETE\", id)",
                "   -> MainWikiFrame.refreshList()",
                "",
                "4) Sync",
                "   Server ADD/DELETE -> save -> broadcast REFRESH",
                "   Client REFRESH -> send LIST",
                "   Server LIST -> send LIST_DATA",
                "   Client LIST_DATA -> applyServerData(list)",
                "",
                "5) Chat",
                "   chat input enter -> send CHAT",
                "   server broadcast CHAT_MSG -> appendChat on clients",
                "",
                "[D] Save Policy",
                "- Offline: save on window close",
                "- Online: server saves right after ADD/DELETE",
                "",
                "[E] Recovery",
                "- connection failure: retry IP/PORT input",
                "- input cancel/unreachable: fallback to offline",
                "- send/receive failure: notify in chat/status",
                "",
                "=========================================================="
        );
    }

    public static void printGuide() {
        System.out.println(getProcessGuide());
    }

    public static void main(String[] args) {
        printGuide();
    }
}