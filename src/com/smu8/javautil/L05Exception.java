package com.smu8.javautil;

public class L05Exception {
    public static record ErpRequest(
            int money
    ) {
        public boolean log() {
            return false;
        }
    }

    public interface Service {
        void process(ErpRequest erpRequest);
    }

    public static class ErpException extends RuntimeException {
    }

    public static class ErpLog implements Service {
        private Service service;
        public ErpLog(Service service) {
            this.service = service;
        }
        public void process(ErpRequest request) {
            try {
                service.process(request);
                System.out.println("[Log]: Processed");
            } catch (ErpException e) {
                System.out.println("[Log]: Error");
                throw e;
            }
        }
    }

    public static class ErpService implements Service{
        public void process(ErpRequest request) {
            if (request.money < 0) {// 예외 처리 해야 하는 상황
                // 예외 처리 코드가 100만줄이라 가정
                throw new ErpException();
            }
            System.out.println(request.money); // 정상 흐름
        }
    }

    public static class ErpExceptionHandler implements  Service {
        private Service service;
        public ErpExceptionHandler(Service service) {
            this.service = service;
        }
        public void process(ErpRequest request) {
            try {
                service.process(request);
            } catch (ErpException e) {
                // 예외 처리 코드 100만줄
                System.out.println("예외 처리");
            }
        }
    }

    public static void main(String[] args) {
        Service service = new ErpExceptionHandler(new ErpLog(new ErpService()));
        service.process(new ErpRequest(1000));
        service.process(new ErpRequest(-1000));
    }
}
