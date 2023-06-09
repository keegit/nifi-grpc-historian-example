package org.mikeyo;

import org.apache.nifi.processors.grpc.FlowFileReply;
import org.apache.nifi.processors.grpc.FlowFileRequest;
import org.apache.nifi.processors.grpc.FlowFileServiceGrpc;
import org.apache.nifi.processors.grpc.HistorianEventCountResponse;

import java.util.logging.Logger;

import io.grpc.stub.StreamObserver;

public class FlowFileService extends FlowFileServiceGrpc.FlowFileServiceImplBase {
    private static final Logger LOG = Logger.getLogger(FlowFileService.class.getName());

    private int numReceived = 0;

    @Override
    public void send(FlowFileRequest request, StreamObserver<FlowFileReply> responseObserver) {
        numReceived++;
        if(numReceived % 10 == 0) {
            LOG.info("Received " + numReceived + " total messages.");
        }
        LOG.info("Received FlowFile: " + request.toString());

        final HistorianEventCountResponse historianResponse = HistorianEventCountResponse.newBuilder()
            .setStart(5)
            .setCount(5)
            .build();

        final FlowFileReply reply = FlowFileReply.newBuilder()
                .setResponseCode(FlowFileReply.ResponseCode.SUCCESS)
                .setBody("Message received successfully.")
                .setHistorianResponse(historianResponse)
                .build();
        LOG.info("hey");
        LOG.info("Replying with: " + reply.toString());
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
