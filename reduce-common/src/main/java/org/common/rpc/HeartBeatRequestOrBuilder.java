// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: server.proto

package org.common.rpc;

public interface HeartBeatRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:jraft.HeartBeatRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * 请求时间
   * </pre>
   *
   * <code>.google.protobuf.UInt64Value timestamp = 1;</code>
   */
  boolean hasTimestamp();
  /**
   * <pre>
   * 请求时间
   * </pre>
   *
   * <code>.google.protobuf.UInt64Value timestamp = 1;</code>
   */
  com.google.protobuf.UInt64Value getTimestamp();
  /**
   * <pre>
   * 请求时间
   * </pre>
   *
   * <code>.google.protobuf.UInt64Value timestamp = 1;</code>
   */
  com.google.protobuf.UInt64ValueOrBuilder getTimestampOrBuilder();

  /**
   * <pre>
   *请求体
   * </pre>
   *
   * <code>bytes payload = 2;</code>
   */
  com.google.protobuf.ByteString getPayload();
}
