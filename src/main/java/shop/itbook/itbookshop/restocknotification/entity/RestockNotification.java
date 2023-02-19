//package shop.itbook.itbookshop.restocknotification.entity;
//
//import java.time.LocalDateTime;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//import javax.persistence.UniqueConstraint;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import shop.itbook.itbookshop.productgroup.product.entity.Product;
//import shop.itbook.itbookshop.membergroup.member.entity.Member;
//
///**
// * 재입고 알림 테이블에 대한 엔티티 입니다.
// *
// * @author 강명관
// * @since 1.0
// */
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//@Table(name = "restock_notification", uniqueConstraints = {
//    @UniqueConstraint(name = "UniqueProductNoAndMemberNo",
//        columnNames = {"product_no", "member_no"})
//})
//public class RestockNotification {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "restock_notification_no")
//    private Long restockNotificationNo;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "product_no", nullable = false)
//    private Product product;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_no", nullable = false)
//    private Member member;
//
//    @Column(name = "notification_created_at", nullable = false, columnDefinition = "default now()")
//    private LocalDateTime notificationCreatedAt;
//
//    /**
//     * 재입고 알림 테이블에 대한 엔티티 생성자 입니다.
//     *
//     * @param product the product
//     * @param member  the member
//     * @author 강명관
//     */
//    @Builder
//    public RestockNotification(Product product, Member member) {
//        this.product = product;
//        this.member = member;
//    }
//}
