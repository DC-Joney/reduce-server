// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: server.proto

package org.common.rpc;

public interface HotKeyReportRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:jraft.HotKeyReportRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string appName = 1;</code>
   */
  java.lang.String getAppName();
  /**
   * <code>string appName = 1;</code>
   */
  com.google.protobuf.ByteString
      getAppNameBytes();

  /**
   * <code>string key = 2;</code>
   */
  java.lang.String getKey();
  /**
   * <code>string key = 2;</code>
   */
  com.google.protobuf.ByteString
      getKeyBytes();

  /**
   * <code>int64 count = 3;</code>
   */
  long getCount();

  /**
   * <code>int64 intervalMs = 4;</code>
   */
  long getIntervalMs();

  /**
   * <code>int64 currentTime = 5;</code>
   */
  long getCurrentTime();
}
