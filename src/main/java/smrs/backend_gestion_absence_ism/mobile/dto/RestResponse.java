package smrs.backend_gestion_absence_ism.mobile.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class RestResponse {

    // Private constructor to prevent instantiation
    private RestResponse() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static Map<String, Object> response(HttpStatus status, Object results, String type) {

        Map<String, Object> response = new HashMap<String, Object>();
        response.put("status", status);
        response.put("results", results);
        response.put("type", type);
        return response;
    }

    public static Map<String, Object> responsePaginate(
            HttpStatus status, Object results, String type,
            Object pages, Object currentPage,
            Object totalPages, Object totalItems,
            Boolean first, Boolean last) {

        Map<String, Object> response = new HashMap<String, Object>();
        response.put("status", status);
        response.put("results", results);
        response.put("type", type);
        response.put("pages", pages);
        response.put("currentPage", currentPage);
        response.put("totalPages", totalPages);
        response.put("totalItems", totalItems);
        response.put("first", first);
        response.put("last", last);
        return response;
    }

    public static Map<String, String> extractFieldErrors(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        fieldErrors
                .forEach(fieldError -> errors.put(fieldError.getField().toLowerCase(), fieldError.getDefaultMessage()));
        return errors;
    }

}
