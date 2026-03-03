package Reproject;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // 1. 데이터 저장소 준비
                ConceptRepository repository = new ConceptRepository();

                // 2. 검색 서비스 준비 (유사도 알고리즘 포함)
                SearchService searchService = new SearchService(repository);

                // 3. 메인 화면 생성 및 실행
                MainWikiFrame frame = new MainWikiFrame(searchService, repository);

                // 화면을 표시합니다.
                frame.setVisible(true);

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("프로그램 실행 중 오류가 발생했습니다: " + e.getMessage());
            }
        });
    }
}