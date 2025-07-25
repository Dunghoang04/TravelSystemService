/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelAgentService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-07-14  1.0        Hung              First implementation
 */
package model;

/**
 *
 * @author Hung
 */
public class RequestCancelFullDTO {

    private RequestCancel requestCancel;
    private BookDetail bookDetail;
    private Tour tour;

    public RequestCancelFullDTO() {
    }

    public RequestCancelFullDTO(RequestCancel requestCancel, BookDetail bookDetail, Tour tour) {
        this.requestCancel = requestCancel;
        this.bookDetail = bookDetail;
        this.tour = tour;
    }

    public RequestCancel getRequestCancel() {
        return requestCancel;
    }

    public void setRequestCancel(RequestCancel requestCancel) {
        this.requestCancel = requestCancel;
    }

    public BookDetail getBookDetail() {
        return bookDetail;
    }

    public void setBookDetail(BookDetail bookDetail) {
        this.bookDetail = bookDetail;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    @Override
public String toString() {
    return "RequestCancelFullDTO {\n" +
            "  RequestCancel = {\n" +
            "    requestCancelID = " + requestCancel.getRequestCancelID() + ",\n" +
            "    bookID = " + requestCancel.getBookID() + ",\n" +
            "    userID = " + requestCancel.getUserID() + ",\n" +
            "    requestDate = " + requestCancel.getRequestDate() + ",\n" +
            "    reason = '" + requestCancel.getReason() + "',\n" +
            "    status = " + requestCancel.getStatus() + "\n" +
            "  },\n" +
            "  BookDetail = {\n" +
            "    bookID = " + bookDetail.getBookID() + ",\n" +
            "    bookCode = " + bookDetail.getBookCode() + ",\n" +
            "    userID = " + bookDetail.getUserID() + ",\n" +
            "    bookDate = " + bookDetail.getBookDate() + ",\n" +
            "    totalPrice = " + bookDetail.getTotalPrice() + ",\n" +
            "    numberAdult = " + bookDetail.getNumberAdult() + ",\n" +
            "    numberChildren = " + bookDetail.getNumberChildren() + ",\n" +
            "    firstName = '" + bookDetail.getFirstName() + "',\n" +
            "    lastName = '" + bookDetail.getLastName() + "',\n" +
            "    phone = '" + bookDetail.getPhone() + "',\n" +
            "    gmail = '" + bookDetail.getGmail() + "',\n" +
            "    note = '" + bookDetail.getNote() + "',\n" +
            "    isBookedForOther = " + bookDetail.getIsBookedForOther() + ",\n" +
            "    status = " + bookDetail.getStatus() + ",\n" +
            "    paymentMethodID = " + bookDetail.getPaymentMethodId()+ ",\n" +
            "    voucherID = " + bookDetail.getVoucherID() + "\n" +
            "  },\n" +
            "  Tour = {\n" +
            "    tourID = " + tour.getTourID() + ",\n" +
            "    tourName = '" + tour.getTourName() + "',\n" +
            "    tourIntroduce = '" + tour.getTourIntroduce() + "',\n" +
            "    tourSchedule = '" + tour.getTourSchedule() + "',\n" +
            "    tourInclude = '" + tour.getTourInclude() + "',\n" +
            "    tourNonInclude = '" + tour.getTourNonInclude() + "',\n" +
            "    numberOfDay = " + tour.getNumberOfDay() + ",\n" +
            "    startPlace = '" + tour.getStartPlace() + "',\n" +
            "    endPlace = '" + tour.getEndPlace() + "',\n" +
            "    startDay = " + tour.getStartDay() + ",\n" +
            "    endDay = " + tour.getEndDay() + ",\n" +
            "    quantity = " + tour.getQuantity() + ",\n" +
            "    adultPrice = " + tour.getAdultPrice() + ",\n" +
            "    childrenPrice = " + tour.getChildrenPrice() + ",\n" +
            "    image = '" + tour.getImage() + "',\n" +
            "    status = " + tour.getStatus() + "\n" +
            "  }\n" +
            '}';
}

    
    
}
