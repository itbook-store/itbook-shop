package shop.itbook.itbookshop.productgroup.product.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.productgroup.product.dto.request.AddProductRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.request.ModifyProductRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.response.AddProductResponseDto;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;

/**
 * 상품 컨트롤러 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
public class ProductRestController {

    private final ProductService productService;

    @PostMapping("/api/products")
    public ResponseEntity<AddProductResponseDto> productAdd(
        @Valid @RequestBody AddProductRequestDto addProductRequestDto,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        AddProductResponseDto productPk = productService.addProduct(addProductRequestDto);

        return new ResponseEntity<>(productPk, headers, HttpStatus.CREATED);
    }

    @PutMapping("/api/products/{productId}")
    public ResponseEntity<Boolean> productModify(@PathVariable Long productId, @Valid @RequestBody
    ModifyProductRequestDto modifyProductRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        productService.modifyProduct(productId, modifyProductRequestDto);

        return new ResponseEntity<>(Boolean.TRUE, headers, HttpStatus.OK);
    }

    @DeleteMapping("/api/products/{productId}")
    public ResponseEntity productRemove(@PathVariable Long productId) {
        boolean isRemoved = productService.removeProduct(productId);

        if (!isRemoved) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }
}
