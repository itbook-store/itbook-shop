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
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.productgroup.product.dto.request.AddProductRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.request.ModifyProductRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.response.AddProductResponseDto;
import shop.itbook.itbookshop.productgroup.product.resultmessageenum.ProductResultMessageEnum;
import shop.itbook.itbookshop.productgroup.product.service.adminapi.ProductService;

/**
 * 상품 컨트롤러 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class ProductAdminController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<CommonResponseBody<AddProductResponseDto>> productAdd(
        @Valid @RequestBody AddProductRequestDto addProductRequestDto) {

        AddProductResponseDto productPk =
            new AddProductResponseDto(productService.addProduct(addProductRequestDto));

        CommonResponseBody<AddProductResponseDto> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(true, HttpStatus.CREATED.value(),
                ProductResultMessageEnum.ADD_SUCCESS.getMessage()), productPk);

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponseBody);

    }

    @PutMapping("/{productId}")
    public ResponseEntity<CommonResponseBody<Boolean>> productModify(
        @PathVariable Long productId,
        @Valid @RequestBody ModifyProductRequestDto modifyProductRequestDto) {
        productService.modifyProduct(productId, modifyProductRequestDto);

        CommonResponseBody<Boolean> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(true, HttpStatus.OK.value(),
                ProductResultMessageEnum.MODIFY_SUCCESS.getMessage()), null);

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<CommonResponseBody<Boolean>> productRemove(@PathVariable Long productId) {
        productService.removeProduct(productId);

        CommonResponseBody<Boolean> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(true, HttpStatus.NO_CONTENT.value(),
                ProductResultMessageEnum.DELETE_SUCCESS.getMessage()), null);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(commonResponseBody);
    }
}
