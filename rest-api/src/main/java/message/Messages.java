package message;

public class Messages {
    public static final int GET = 0;
    public static final int POST = 1;
    public static final int PUT = 2;
    public static final int DELETE = 3;
    private Message[] messages = new Message[]{
            new Message("GET",200,null),
            new Message("POST",200,null),
            new Message("PUT",200,null),
            new Message("DELETE",200,null),
    };

    public Messages(){}

    public Message[] getMessages() {
        return messages;
    }

    public class Message {
        private String msg;
        private int code;
        private Object data;


        public Message(String msg, int code, Object data) {
            this.msg = msg;
            this.code = code;
            this.data = data;
        }

        public String getMsg() {
            return msg;
        }

        public Message setMsg(String msg) {
            this.msg = msg;
            return this;
        }

        public int getCode() {
            return code;
        }

        public Message setCode(int code) {
            this.code = code;
            return this;
        }

        public Object getData() {
            return data;
        }

        public Message setData(Object data) {
            this.data = data;
            return this;
        }

    }

}
