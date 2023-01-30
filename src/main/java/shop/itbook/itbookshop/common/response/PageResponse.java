package shop.itbook.itbookshop.common.response;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;


/**
 * @author 최겸준
 * @since 1.0
 */
@NoArgsConstructor
@Getter
public class PageResponse<T> {

    private List<T> content;

    private int totalPages;

    private int number;

    private boolean previous;

    private boolean next;

    /**
     * Instantiates a new Page response dto.
     *
     * @param result the result
     */
    public PageResponse(Page<T> result) {

        this.content = result.getContent();

        this.totalPages = result.getTotalPages();

        this.number = result.getNumber();

        this.previous = result.hasPrevious();

        this.next = result.hasNext();
    }


}