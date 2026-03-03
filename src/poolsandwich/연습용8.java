//public class 연습용8 {
//
//package poolsandwich;
//    public void confirmOutbound(OutboundOrder order) {
//
//        fdaValidator.validate(order); // LOT / EXP / SERIAL
//
//        FdaResponse res = fdaClient.submit(order);
//
//        if (!res.isApproved()) {
//            throw new ComplianceException("FDA 승인 실패");
//        }
//
//        order.complete();
//    }
//
//}
