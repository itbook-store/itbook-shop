package shop.itbook.itbookshop.init;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class TokenInterceptor implements HandlerInterceptor {

    private final TokenManager tokenManager;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        if (!isTokenExist() || !isTokenValid()) {
            tokenManager.requestToken();
        }
        return true;
    }

    public boolean isTokenExist() {
        if (Objects.isNull(tokenManager.getItBookObjectStorageToken())) {
            return false;
        }
        return true;
    }

    public boolean isTokenValid() throws ParseException {
        Date tokenExpiresOrigin =
            parseStringToDate(
                tokenManager.getItBookObjectStorageToken().getExpires());
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, -5);
        Date currentTime = parseStringToDate(sdf.format(cal.getTime()));
        if (tokenExpiresOrigin.after(currentTime)) {
            return false;
        }
        return true;
    }

    private Date parseStringToDate(String stringDate) throws ParseException {
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.parse(stringDate);
    }

}