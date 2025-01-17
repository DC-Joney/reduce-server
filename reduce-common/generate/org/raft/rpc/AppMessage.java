// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: consistency.proto

package org.raft.rpc;

/**
 * Protobuf type {@code AppMessage}
 */
public  final class AppMessage extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:AppMessage)
    AppMessageOrBuilder {
private static final long serialVersionUID = 0L;
  // Use AppMessage.newBuilder() to construct.
  private AppMessage(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private AppMessage() {
    appName_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new AppMessage();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private AppMessage(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 10: {
            java.lang.String s = input.readStringRequireUtf8();

            appName_ = s;
            break;
          }
          case 16: {

            startTime_ = input.readInt64();
            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return org.raft.rpc.Consistency.internal_static_AppMessage_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return org.raft.rpc.Consistency.internal_static_AppMessage_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            org.raft.rpc.AppMessage.class, org.raft.rpc.AppMessage.Builder.class);
  }

  public static final int APPNAME_FIELD_NUMBER = 1;
  private volatile java.lang.Object appName_;
  /**
   * <code>string appName = 1;</code>
   */
  public java.lang.String getAppName() {
    java.lang.Object ref = appName_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      appName_ = s;
      return s;
    }
  }
  /**
   * <code>string appName = 1;</code>
   */
  public com.google.protobuf.ByteString
      getAppNameBytes() {
    java.lang.Object ref = appName_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      appName_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int STARTTIME_FIELD_NUMBER = 2;
  private long startTime_;
  /**
   * <code>int64 startTime = 2;</code>
   */
  public long getStartTime() {
    return startTime_;
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!getAppNameBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, appName_);
    }
    if (startTime_ != 0L) {
      output.writeInt64(2, startTime_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!getAppNameBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, appName_);
    }
    if (startTime_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt64Size(2, startTime_);
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof org.raft.rpc.AppMessage)) {
      return super.equals(obj);
    }
    org.raft.rpc.AppMessage other = (org.raft.rpc.AppMessage) obj;

    if (!getAppName()
        .equals(other.getAppName())) return false;
    if (getStartTime()
        != other.getStartTime()) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + APPNAME_FIELD_NUMBER;
    hash = (53 * hash) + getAppName().hashCode();
    hash = (37 * hash) + STARTTIME_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getStartTime());
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static org.raft.rpc.AppMessage parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.raft.rpc.AppMessage parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.raft.rpc.AppMessage parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.raft.rpc.AppMessage parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.raft.rpc.AppMessage parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.raft.rpc.AppMessage parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.raft.rpc.AppMessage parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.raft.rpc.AppMessage parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.raft.rpc.AppMessage parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static org.raft.rpc.AppMessage parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.raft.rpc.AppMessage parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.raft.rpc.AppMessage parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(org.raft.rpc.AppMessage prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code AppMessage}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:AppMessage)
      org.raft.rpc.AppMessageOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return org.raft.rpc.Consistency.internal_static_AppMessage_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return org.raft.rpc.Consistency.internal_static_AppMessage_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              org.raft.rpc.AppMessage.class, org.raft.rpc.AppMessage.Builder.class);
    }

    // Construct using org.raft.rpc.AppMessage.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      appName_ = "";

      startTime_ = 0L;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return org.raft.rpc.Consistency.internal_static_AppMessage_descriptor;
    }

    @java.lang.Override
    public org.raft.rpc.AppMessage getDefaultInstanceForType() {
      return org.raft.rpc.AppMessage.getDefaultInstance();
    }

    @java.lang.Override
    public org.raft.rpc.AppMessage build() {
      org.raft.rpc.AppMessage result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public org.raft.rpc.AppMessage buildPartial() {
      org.raft.rpc.AppMessage result = new org.raft.rpc.AppMessage(this);
      result.appName_ = appName_;
      result.startTime_ = startTime_;
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof org.raft.rpc.AppMessage) {
        return mergeFrom((org.raft.rpc.AppMessage)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(org.raft.rpc.AppMessage other) {
      if (other == org.raft.rpc.AppMessage.getDefaultInstance()) return this;
      if (!other.getAppName().isEmpty()) {
        appName_ = other.appName_;
        onChanged();
      }
      if (other.getStartTime() != 0L) {
        setStartTime(other.getStartTime());
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      org.raft.rpc.AppMessage parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (org.raft.rpc.AppMessage) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private java.lang.Object appName_ = "";
    /**
     * <code>string appName = 1;</code>
     */
    public java.lang.String getAppName() {
      java.lang.Object ref = appName_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        appName_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string appName = 1;</code>
     */
    public com.google.protobuf.ByteString
        getAppNameBytes() {
      java.lang.Object ref = appName_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        appName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string appName = 1;</code>
     */
    public Builder setAppName(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      appName_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string appName = 1;</code>
     */
    public Builder clearAppName() {
      
      appName_ = getDefaultInstance().getAppName();
      onChanged();
      return this;
    }
    /**
     * <code>string appName = 1;</code>
     */
    public Builder setAppNameBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      appName_ = value;
      onChanged();
      return this;
    }

    private long startTime_ ;
    /**
     * <code>int64 startTime = 2;</code>
     */
    public long getStartTime() {
      return startTime_;
    }
    /**
     * <code>int64 startTime = 2;</code>
     */
    public Builder setStartTime(long value) {
      
      startTime_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 startTime = 2;</code>
     */
    public Builder clearStartTime() {
      
      startTime_ = 0L;
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:AppMessage)
  }

  // @@protoc_insertion_point(class_scope:AppMessage)
  private static final org.raft.rpc.AppMessage DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new org.raft.rpc.AppMessage();
  }

  public static org.raft.rpc.AppMessage getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<AppMessage>
      PARSER = new com.google.protobuf.AbstractParser<AppMessage>() {
    @java.lang.Override
    public AppMessage parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new AppMessage(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<AppMessage> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<AppMessage> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public org.raft.rpc.AppMessage getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

