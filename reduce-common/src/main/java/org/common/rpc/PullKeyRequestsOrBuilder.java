// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: server.proto

package org.common.rpc;

public interface PullKeyRequestsOrBuilder extends
    // @@protoc_insertion_point(interface_extends:jraft.PullKeyRequests)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>repeated .jraft.PullKeyRequest requests = 1;</code>
   */
  java.util.List<org.common.rpc.PullKeyRequest> 
      getRequestsList();
  /**
   * <code>repeated .jraft.PullKeyRequest requests = 1;</code>
   */
  org.common.rpc.PullKeyRequest getRequests(int index);
  /**
   * <code>repeated .jraft.PullKeyRequest requests = 1;</code>
   */
  int getRequestsCount();
  /**
   * <code>repeated .jraft.PullKeyRequest requests = 1;</code>
   */
  java.util.List<? extends org.common.rpc.PullKeyRequestOrBuilder> 
      getRequestsOrBuilderList();
  /**
   * <code>repeated .jraft.PullKeyRequest requests = 1;</code>
   */
  org.common.rpc.PullKeyRequestOrBuilder getRequestsOrBuilder(
      int index);
}
