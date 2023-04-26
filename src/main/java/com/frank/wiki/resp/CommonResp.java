package com.frank.wiki.resp;



import lombok.Data;
import lombok.Getter;
import lombok.Setter;
//    @Getter
//    @Setter
@Data
public class CommonResp<T> {
    /**
     * 业务上的成功或失败
     */
    private boolean success = true;

    /**
     * 返回信息描述，对本次响应作解释
     */
    private String message;

    /**
     * 返回泛数据，自定义类型
     */
    private T content;

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("ResponseDto{");
            sb.append("success=").append(success);
            sb.append(", message='").append(message).append('\'');
            sb.append(", content=").append(content);
            sb.append('}');
            return sb.toString();
        }

//    public boolean isSuccess() {
//        return success;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public T getContent() {
//        return content;
//    }
//
//    public void setSuccess(boolean success) {
//        this.success = success;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public void setContent(T content) {
//        this.content = content;
//    }
}
