package shop.itbook.itbookshop.fileservice.dto;

import java.io.Serializable;
import lombok.Data;

/**
 * 오브젝트 스토리지 토큰에 대한 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@Data
public class ItBookObjectStorageToken implements Serializable {
    private Access access;

}


