// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: consistency.proto

package org.raft.rpc;

public interface SearchKeyResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:SearchKeyResponse)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.Response response = 2;</code>
   */
  boolean hasResponse();
  /**
   * <code>.Response response = 2;</code>
   */
  org.raft.rpc.Response getResponse();
  /**
   * <code>.Response response = 2;</code>
   */
  org.raft.rpc.ResponseOrBuilder getResponseOrBuilder();

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
   * <code>string key = 3;</code>
   */
  java.lang.String getKey();
  /**
   * <code>string key = 3;</code>
   */
  com.google.protobuf.ByteString
      getKeyBytes();

  /**
   * <code>uint64 count = 4;</code>
   */
  long getCount();
}