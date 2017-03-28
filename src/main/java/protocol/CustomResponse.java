package protocol;

import java.io.Serializable;

public class CustomResponse implements Serializable{


    Object data;

    public CustomResponse(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
