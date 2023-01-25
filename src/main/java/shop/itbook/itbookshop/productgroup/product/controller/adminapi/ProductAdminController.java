package shop.itbook.itbookshop.productgroup.product.controller.adminapi;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.productgroup.product.dto.request.AddProductBookRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.request.AddProductRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.request.ModifyProductRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.response.AddProductResponseDto;
import shop.itbook.itbookshop.productgroup.product.resultmessageenum.ProductResultMessageEnum;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;

/**
 * 관리자의 요청을 받고 반환하는 상품 Controller 클래스입니다.
 *
 * @author 이하늬 * @since 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class ProductAdminController {

    private final ProductService productService;

//    /**
//     * 상품 등록을 요청하는 메서드입니다.
//     *
//     * @param addProductRequestDto 상품 등록을 위한 정보를 바인딩 받는 dto 객체입니다.
//     * @return 등록한 상품의 상품 번호를 response entity에 담아 반환합니다.
//     * @author 이하늬
//     */
//    @PostMapping
//    public ResponseEntity<CommonResponseBody<AddProductResponseDto>> productAdd(
//        @Valid @RequestBody AddProductRequestDto addProductRequestDto) {
//
//        AddProductResponseDto productPk =
//            new AddProductResponseDto(productService.addProduct(addProductRequestDto));
//
//        CommonResponseBody<AddProductResponseDto> commonResponseBody = new CommonResponseBody<>(
//            new CommonResponseBody.CommonHeader(
//                ProductResultMessageEnum.ADD_SUCCESS.getMessage()), productPk);
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponseBody);
//    }

    /**
     * 상품 및 도서 등록을 요청하는 메서드입니다.
     *
     * @param requestDto 상품(도서) 등록을 위한 정보를 바인딩 받는 dto 객체입니다.
     * @return 등록한 상품의 상품 번호를 response entity에 담아 반환합니다.
     * @author 이하늬
     */
    @PostMapping
    public ResponseEntity<CommonResponseBody<AddProductResponseDto>> productAdd(
        @RequestPart AddProductBookRequestDto requestDto,
        @RequestPart MultipartFile thumbnails,
        @RequestPart(required = false) MultipartFile ebook) {

        AddProductResponseDto productPk =
            new AddProductResponseDto(productService.addProduct(requestDto, thumbnails, ebook));

        CommonResponseBody<AddProductResponseDto> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                ProductResultMessageEnum.ADD_SUCCESS.getMessage()), productPk);

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponseBody);
    }


    /**
     * 상품 수정을 요청하는 메서드입니다.
     *
     * @param productId               상품 번호입니다.
     * @param modifyProductRequestDto 상품 수정을 위한 정보를 바인딩 받는 dto 객체입니다.
     * @return 성공 메세지를 response entity에 담아 반환합니다.
     * @author 이하늬
     */
    @PutMapping("/{productId}")
    public ResponseEntity<CommonResponseBody<Void>> productModify(
        @PathVariable Long productId,
        @Valid @RequestBody ModifyProductRequestDto modifyProductRequestDto) {
        productService.modifyProduct(productId, modifyProductRequestDto);

        CommonResponseBody<Void> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                ProductResultMessageEnum.MODIFY_SUCCESS.getMessage()), null);

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }

    /**
     * 상품 삭제를 요청하는 메서드입니다.
     *
     * @param productId 상품 번호입니다.
     * @return 성공 메세지를 response entity에 담아 반환합니다.
     * @author 이하늬
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<CommonResponseBody<Void>> productRemove(@PathVariable Long productId) {
        productService.removeProduct(productId);

        CommonResponseBody<Void> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                ProductResultMessageEnum.DELETE_SUCCESS.getMessage()), null);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(commonResponseBody);
    }
}
