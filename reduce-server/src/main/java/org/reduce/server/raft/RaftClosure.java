package org.reduce.server.raft;

import com.alipay.sofa.jraft.Closure;
import com.alipay.sofa.jraft.Status;
import com.alipay.sofa.jraft.error.RaftError;
import com.google.protobuf.Message;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

public class RaftClosure implements Closure {

    private final Closure closure;

    /**
     * 请求的message信息
     */
    @Getter
    private final Message message;

    /**
     * 最终返回的状态
     */
    private final RaftStatus raftStatus = new RaftStatus();

    public RaftClosure( Message message,Closure closure) {
        this.closure = closure;
        this.message = message;
    }

    @Override
    public void run(Status status) {

        raftStatus.setStatus(status);
        closure.run(raftStatus);
    }


    public void setResponse(Message response){
        raftStatus.setResponse(response);
    }

    public void setThrowable(Throwable throwable){
        raftStatus.setThrowable(throwable);
    }

    @Setter
    @Getter
    public static class RaftStatus extends Status {

        private Message response;

        private Throwable throwable;

        private Status status;


        @Override
        public void reset() {
            status.reset();
        }

        @Override
        public boolean isOk() {
            return status.isOk();
        }

        @Override
        public int getCode() {
            return status.getCode();
        }

        @Override
        public void setCode(int code) {
            status.setCode(code);
        }

        @Override
        public RaftError getRaftError() {
            return status.getRaftError();
        }

        @Override
        public void setError(int code, String fmt, Object... args) {
            status.setError(code, fmt, args);
        }

        @Override
        public void setError(RaftError error, String fmt, Object... args) {
            status.setError(error, fmt, args);
        }

        @Override
        public String toString() {
            return status.toString();
        }

        @Override
        public Status copy() {
            RaftStatus copy = new RaftStatus();
            copy.status = this.status;
            copy.response = this.response;
            copy.throwable = this.throwable;
            return copy;
        }

        @Override
        public String getErrorMsg() {
            return status.getErrorMsg();
        }

        @Override
        public void setErrorMsg(String errMsg) {
            status.setErrorMsg(errMsg);
        }
    }
}
