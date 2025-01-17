// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: consistency.proto

package org.common.raft;

public final class Consistency {
  private Consistency() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_HotKeyRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_HotKeyRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_SearchKeyRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_SearchKeyRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_SearchKeyResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_SearchKeyResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_Response_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_Response_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_AppMessage_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_AppMessage_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_TestWriteRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_TestWriteRequest_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\021consistency.proto\032\037google/protobuf/tim" +
      "estamp.proto\"j\n\rHotKeyRequest\022\022\n\ncreateT" +
      "ime\030\001 \001(\003\022\013\n\003key\030\002 \001(\t\022\017\n\007appName\030\003 \001(\t\022" +
      "\r\n\005count\030\004 \001(\005\022\030\n\004type\030\005 \001(\0162\n.WriteType" +
      "\"0\n\020SearchKeyRequest\022\017\n\007appName\030\001 \001(\t\022\013\n" +
      "\003key\030\003 \001(\t\"]\n\021SearchKeyResponse\022\033\n\010respo" +
      "nse\030\002 \001(\0132\t.Response\022\017\n\007appName\030\001 \001(\t\022\013\n" +
      "\003key\030\003 \001(\t\022\r\n\005count\030\004 \001(\004\"9\n\010Response\022\016\n" +
      "\006errMsg\030\002 \001(\t\022\017\n\007success\030\003 \001(\010\022\014\n\004data\030\004" +
      " \001(\014\"0\n\nAppMessage\022\017\n\007appName\030\001 \001(\t\022\021\n\ts" +
      "tartTime\030\002 \001(\003\"2\n\020TestWriteRequest\022\017\n\007ap" +
      "pName\030\001 \001(\t\022\r\n\005value\030\002 \001(\t*\"\n\tWriteType\022" +
      "\t\n\005WRITE\020\000\022\n\n\006DELETE\020\00122\n\tBiRequest\022%\n\007r" +
      "equest\022\013.AppMessage\032\t.Response\"\0000\001B\023\n\017or" +
      "g.common.raftP\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.google.protobuf.TimestampProto.getDescriptor(),
        });
    internal_static_HotKeyRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_HotKeyRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_HotKeyRequest_descriptor,
        new java.lang.String[] { "CreateTime", "Key", "AppName", "Count", "Type", });
    internal_static_SearchKeyRequest_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_SearchKeyRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_SearchKeyRequest_descriptor,
        new java.lang.String[] { "AppName", "Key", });
    internal_static_SearchKeyResponse_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_SearchKeyResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_SearchKeyResponse_descriptor,
        new java.lang.String[] { "Response", "AppName", "Key", "Count", });
    internal_static_Response_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_Response_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_Response_descriptor,
        new java.lang.String[] { "ErrMsg", "Success", "Data", });
    internal_static_AppMessage_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_AppMessage_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_AppMessage_descriptor,
        new java.lang.String[] { "AppName", "StartTime", });
    internal_static_TestWriteRequest_descriptor =
      getDescriptor().getMessageTypes().get(5);
    internal_static_TestWriteRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_TestWriteRequest_descriptor,
        new java.lang.String[] { "AppName", "Value", });
    com.google.protobuf.TimestampProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
