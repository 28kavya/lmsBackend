package com.learnhub.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseApi {
    String message;
    Object data;

}
