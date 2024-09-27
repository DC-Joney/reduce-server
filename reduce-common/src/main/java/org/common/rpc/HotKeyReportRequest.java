// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: server.proto

package org.common.rpc;

/**
 * Protobuf type {@code jraft.HotKeyReportRequest}
 */
public  final class HotKeyReportRequest extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:jraft.HotKeyReportRequest)
    HotKeyReportRequestOrBuilder {
private static final long serialVersionUID = 0L;
  // Use HotKeyReportRequest.newBuilder() to construct.
  private HotKeyReportRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private HotKeyReportRequest() {
    appName_ = "";
    key_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new HotKeyReportRequest();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private HotKeyReportRequest(
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
          case 18: {
            java.lang.String s = input.readStringRequireUtf8();

            key_ = s;
            break;
          }
          case 24: {

            count_ = input.readInt64();
            break;
          }
          case 32: {

            intervalMs_ = input.readInt64();
            break;
          }
          case 40: {

            currentTime_ = input.readInt64();
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
    return org.common.rpc.Server.internal_static_jraft_HotKeyReportRequest_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return org.common.rpc.Server.internal_static_jraft_HotKeyReportRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            org.common.rpc.HotKeyReportRequest.class, org.common.rpc.HotKeyReportRequest.Builder.class);
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

  public static final int KEY_FIELD_NUMBER = 2;
  private volatile java.lang.Object key_;
  /**
   * <code>string key = 2;</code>
   */
  public java.lang.String getKey() {
    java.lang.Object ref = key_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      key_ = s;
      return s;
    }
  }
  /**
   * <code>string key = 2;</code>
   */
  public com.google.protobuf.ByteString
      getKeyBytes() {
    java.lang.Object ref = key_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      key_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int COUNT_FIELD_NUMBER = 3;
  private long count_;
  /**
   * <code>int64 count = 3;</code>
   */
  public long getCount() {
    return count_;
  }

  public static final int INTERVALMS_FIELD_NUMBER = 4;
  private long intervalMs_;
  /**
   * <code>int64 intervalMs = 4;</code>
   */
  public long getIntervalMs() {
    return intervalMs_;
  }

  public static final int CURRENTTIME_FIELD_NUMBER = 5;
  private long currentTime_;
  /**
   * <code>int64 currentTime = 5;</code>
   */
  public long getCurrentTime() {
    return currentTime_;
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
    if (!getKeyBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, key_);
    }
    if (count_ != 0L) {
      output.writeInt64(3, count_);
    }
    if (intervalMs_ != 0L) {
      output.writeInt64(4, intervalMs_);
    }
    if (currentTime_ != 0L) {
      output.writeInt64(5, currentTime_);
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
    if (!getKeyBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, key_);
    }
    if (count_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt64Size(3, count_);
    }
    if (intervalMs_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt64Size(4, intervalMs_);
    }
    if (currentTime_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt64Size(5, currentTime_);
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
    if (!(obj instanceof org.common.rpc.HotKeyReportRequest)) {
      return super.equals(obj);
    }
    org.common.rpc.HotKeyReportRequest other = (org.common.rpc.HotKeyReportRequest) obj;

    if (!getAppName()
        .equals(other.getAppName())) return false;
    if (!getKey()
        .equals(other.getKey())) return false;
    if (getCount()
        != other.getCount()) return false;
    if (getIntervalMs()
        != other.getIntervalMs()) return false;
    if (getCurrentTime()
        != other.getCurrentTime()) return false;
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
    hash = (37 * hash) + KEY_FIELD_NUMBER;
    hash = (53 * hash) + getKey().hashCode();
    hash = (37 * hash) + COUNT_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getCount());
    hash = (37 * hash) + INTERVALMS_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getIntervalMs());
    hash = (37 * hash) + CURRENTTIME_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getCurrentTime());
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static org.common.rpc.HotKeyReportRequest parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.common.rpc.HotKeyReportRequest parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.common.rpc.HotKeyReportRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.common.rpc.HotKeyReportRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.common.rpc.HotKeyReportRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.common.rpc.HotKeyReportRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.common.rpc.HotKeyReportRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.common.rpc.HotKeyReportRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.common.rpc.HotKeyReportRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static org.common.rpc.HotKeyReportRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.common.rpc.HotKeyReportRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.common.rpc.HotKeyReportRequest parseFrom(
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
  public static Builder newBuilder(org.common.rpc.HotKeyReportRequest prototype) {
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
   * Protobuf type {@code jraft.HotKeyReportRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:jraft.HotKeyReportRequest)
      org.common.rpc.HotKeyReportRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return org.common.rpc.Server.internal_static_jraft_HotKeyReportRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return org.common.rpc.Server.internal_static_jraft_HotKeyReportRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              org.common.rpc.HotKeyReportRequest.class, org.common.rpc.HotKeyReportRequest.Builder.class);
    }

    // Construct using org.common.rpc.HotKeyReportRequest.newBuilder()
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

      key_ = "";

      count_ = 0L;

      intervalMs_ = 0L;

      currentTime_ = 0L;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return org.common.rpc.Server.internal_static_jraft_HotKeyReportRequest_descriptor;
    }

    @java.lang.Override
    public org.common.rpc.HotKeyReportRequest getDefaultInstanceForType() {
      return org.common.rpc.HotKeyReportRequest.getDefaultInstance();
    }

    @java.lang.Override
    public org.common.rpc.HotKeyReportRequest build() {
      org.common.rpc.HotKeyReportRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public org.common.rpc.HotKeyReportRequest buildPartial() {
      org.common.rpc.HotKeyReportRequest result = new org.common.rpc.HotKeyReportRequest(this);
      result.appName_ = appName_;
      result.key_ = key_;
      result.count_ = count_;
      result.intervalMs_ = intervalMs_;
      result.currentTime_ = currentTime_;
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
      if (other instanceof org.common.rpc.HotKeyReportRequest) {
        return mergeFrom((org.common.rpc.HotKeyReportRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(org.common.rpc.HotKeyReportRequest other) {
      if (other == org.common.rpc.HotKeyReportRequest.getDefaultInstance()) return this;
      if (!other.getAppName().isEmpty()) {
        appName_ = other.appName_;
        onChanged();
      }
      if (!other.getKey().isEmpty()) {
        key_ = other.key_;
        onChanged();
      }
      if (other.getCount() != 0L) {
        setCount(other.getCount());
      }
      if (other.getIntervalMs() != 0L) {
        setIntervalMs(other.getIntervalMs());
      }
      if (other.getCurrentTime() != 0L) {
        setCurrentTime(other.getCurrentTime());
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
      org.common.rpc.HotKeyReportRequest parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (org.common.rpc.HotKeyReportRequest) e.getUnfinishedMessage();
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

    private java.lang.Object key_ = "";
    /**
     * <code>string key = 2;</code>
     */
    public java.lang.String getKey() {
      java.lang.Object ref = key_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        key_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string key = 2;</code>
     */
    public com.google.protobuf.ByteString
        getKeyBytes() {
      java.lang.Object ref = key_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        key_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string key = 2;</code>
     */
    public Builder setKey(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      key_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string key = 2;</code>
     */
    public Builder clearKey() {
      
      key_ = getDefaultInstance().getKey();
      onChanged();
      return this;
    }
    /**
     * <code>string key = 2;</code>
     */
    public Builder setKeyBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      key_ = value;
      onChanged();
      return this;
    }

    private long count_ ;
    /**
     * <code>int64 count = 3;</code>
     */
    public long getCount() {
      return count_;
    }
    /**
     * <code>int64 count = 3;</code>
     */
    public Builder setCount(long value) {
      
      count_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 count = 3;</code>
     */
    public Builder clearCount() {
      
      count_ = 0L;
      onChanged();
      return this;
    }

    private long intervalMs_ ;
    /**
     * <code>int64 intervalMs = 4;</code>
     */
    public long getIntervalMs() {
      return intervalMs_;
    }
    /**
     * <code>int64 intervalMs = 4;</code>
     */
    public Builder setIntervalMs(long value) {
      
      intervalMs_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 intervalMs = 4;</code>
     */
    public Builder clearIntervalMs() {
      
      intervalMs_ = 0L;
      onChanged();
      return this;
    }

    private long currentTime_ ;
    /**
     * <code>int64 currentTime = 5;</code>
     */
    public long getCurrentTime() {
      return currentTime_;
    }
    /**
     * <code>int64 currentTime = 5;</code>
     */
    public Builder setCurrentTime(long value) {
      
      currentTime_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 currentTime = 5;</code>
     */
    public Builder clearCurrentTime() {
      
      currentTime_ = 0L;
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


    // @@protoc_insertion_point(builder_scope:jraft.HotKeyReportRequest)
  }

  // @@protoc_insertion_point(class_scope:jraft.HotKeyReportRequest)
  private static final org.common.rpc.HotKeyReportRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new org.common.rpc.HotKeyReportRequest();
  }

  public static org.common.rpc.HotKeyReportRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<HotKeyReportRequest>
      PARSER = new com.google.protobuf.AbstractParser<HotKeyReportRequest>() {
    @java.lang.Override
    public HotKeyReportRequest parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new HotKeyReportRequest(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<HotKeyReportRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<HotKeyReportRequest> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public org.common.rpc.HotKeyReportRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

