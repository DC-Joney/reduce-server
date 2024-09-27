// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: consistency.proto

package org.raft.rpc;

/**
 * Protobuf type {@code KeyDistributionRequest}
 */
public  final class KeyDistributionRequest extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:KeyDistributionRequest)
    KeyDistributionRequestOrBuilder {
private static final long serialVersionUID = 0L;
  // Use KeyDistributionRequest.newBuilder() to construct.
  private KeyDistributionRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private KeyDistributionRequest() {
    workerId_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new KeyDistributionRequest();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private KeyDistributionRequest(
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

            workerId_ = s;
            break;
          }
          case 18: {
            org.raft.rpc.HotKeyRequest.Builder subBuilder = null;
            if (request_ != null) {
              subBuilder = request_.toBuilder();
            }
            request_ = input.readMessage(org.raft.rpc.HotKeyRequest.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(request_);
              request_ = subBuilder.buildPartial();
            }

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
    return org.raft.rpc.Consistency.internal_static_KeyDistributionRequest_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return org.raft.rpc.Consistency.internal_static_KeyDistributionRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            org.raft.rpc.KeyDistributionRequest.class, org.raft.rpc.KeyDistributionRequest.Builder.class);
  }

  public static final int WORKERID_FIELD_NUMBER = 1;
  private volatile java.lang.Object workerId_;
  /**
   * <code>string workerId = 1;</code>
   */
  public java.lang.String getWorkerId() {
    java.lang.Object ref = workerId_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      workerId_ = s;
      return s;
    }
  }
  /**
   * <code>string workerId = 1;</code>
   */
  public com.google.protobuf.ByteString
      getWorkerIdBytes() {
    java.lang.Object ref = workerId_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      workerId_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int REQUEST_FIELD_NUMBER = 2;
  private org.raft.rpc.HotKeyRequest request_;
  /**
   * <code>.HotKeyRequest request = 2;</code>
   */
  public boolean hasRequest() {
    return request_ != null;
  }
  /**
   * <code>.HotKeyRequest request = 2;</code>
   */
  public org.raft.rpc.HotKeyRequest getRequest() {
    return request_ == null ? org.raft.rpc.HotKeyRequest.getDefaultInstance() : request_;
  }
  /**
   * <code>.HotKeyRequest request = 2;</code>
   */
  public org.raft.rpc.HotKeyRequestOrBuilder getRequestOrBuilder() {
    return getRequest();
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
    if (!getWorkerIdBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, workerId_);
    }
    if (request_ != null) {
      output.writeMessage(2, getRequest());
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!getWorkerIdBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, workerId_);
    }
    if (request_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(2, getRequest());
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
    if (!(obj instanceof org.raft.rpc.KeyDistributionRequest)) {
      return super.equals(obj);
    }
    org.raft.rpc.KeyDistributionRequest other = (org.raft.rpc.KeyDistributionRequest) obj;

    if (!getWorkerId()
        .equals(other.getWorkerId())) return false;
    if (hasRequest() != other.hasRequest()) return false;
    if (hasRequest()) {
      if (!getRequest()
          .equals(other.getRequest())) return false;
    }
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
    hash = (37 * hash) + WORKERID_FIELD_NUMBER;
    hash = (53 * hash) + getWorkerId().hashCode();
    if (hasRequest()) {
      hash = (37 * hash) + REQUEST_FIELD_NUMBER;
      hash = (53 * hash) + getRequest().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static org.raft.rpc.KeyDistributionRequest parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.raft.rpc.KeyDistributionRequest parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.raft.rpc.KeyDistributionRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.raft.rpc.KeyDistributionRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.raft.rpc.KeyDistributionRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.raft.rpc.KeyDistributionRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.raft.rpc.KeyDistributionRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.raft.rpc.KeyDistributionRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.raft.rpc.KeyDistributionRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static org.raft.rpc.KeyDistributionRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.raft.rpc.KeyDistributionRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.raft.rpc.KeyDistributionRequest parseFrom(
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
  public static Builder newBuilder(org.raft.rpc.KeyDistributionRequest prototype) {
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
   * Protobuf type {@code KeyDistributionRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:KeyDistributionRequest)
      org.raft.rpc.KeyDistributionRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return org.raft.rpc.Consistency.internal_static_KeyDistributionRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return org.raft.rpc.Consistency.internal_static_KeyDistributionRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              org.raft.rpc.KeyDistributionRequest.class, org.raft.rpc.KeyDistributionRequest.Builder.class);
    }

    // Construct using org.raft.rpc.KeyDistributionRequest.newBuilder()
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
      workerId_ = "";

      if (requestBuilder_ == null) {
        request_ = null;
      } else {
        request_ = null;
        requestBuilder_ = null;
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return org.raft.rpc.Consistency.internal_static_KeyDistributionRequest_descriptor;
    }

    @java.lang.Override
    public org.raft.rpc.KeyDistributionRequest getDefaultInstanceForType() {
      return org.raft.rpc.KeyDistributionRequest.getDefaultInstance();
    }

    @java.lang.Override
    public org.raft.rpc.KeyDistributionRequest build() {
      org.raft.rpc.KeyDistributionRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public org.raft.rpc.KeyDistributionRequest buildPartial() {
      org.raft.rpc.KeyDistributionRequest result = new org.raft.rpc.KeyDistributionRequest(this);
      result.workerId_ = workerId_;
      if (requestBuilder_ == null) {
        result.request_ = request_;
      } else {
        result.request_ = requestBuilder_.build();
      }
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
      if (other instanceof org.raft.rpc.KeyDistributionRequest) {
        return mergeFrom((org.raft.rpc.KeyDistributionRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(org.raft.rpc.KeyDistributionRequest other) {
      if (other == org.raft.rpc.KeyDistributionRequest.getDefaultInstance()) return this;
      if (!other.getWorkerId().isEmpty()) {
        workerId_ = other.workerId_;
        onChanged();
      }
      if (other.hasRequest()) {
        mergeRequest(other.getRequest());
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
      org.raft.rpc.KeyDistributionRequest parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (org.raft.rpc.KeyDistributionRequest) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private java.lang.Object workerId_ = "";
    /**
     * <code>string workerId = 1;</code>
     */
    public java.lang.String getWorkerId() {
      java.lang.Object ref = workerId_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        workerId_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string workerId = 1;</code>
     */
    public com.google.protobuf.ByteString
        getWorkerIdBytes() {
      java.lang.Object ref = workerId_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        workerId_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string workerId = 1;</code>
     */
    public Builder setWorkerId(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      workerId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string workerId = 1;</code>
     */
    public Builder clearWorkerId() {
      
      workerId_ = getDefaultInstance().getWorkerId();
      onChanged();
      return this;
    }
    /**
     * <code>string workerId = 1;</code>
     */
    public Builder setWorkerIdBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      workerId_ = value;
      onChanged();
      return this;
    }

    private org.raft.rpc.HotKeyRequest request_;
    private com.google.protobuf.SingleFieldBuilderV3<
        org.raft.rpc.HotKeyRequest, org.raft.rpc.HotKeyRequest.Builder, org.raft.rpc.HotKeyRequestOrBuilder> requestBuilder_;
    /**
     * <code>.HotKeyRequest request = 2;</code>
     */
    public boolean hasRequest() {
      return requestBuilder_ != null || request_ != null;
    }
    /**
     * <code>.HotKeyRequest request = 2;</code>
     */
    public org.raft.rpc.HotKeyRequest getRequest() {
      if (requestBuilder_ == null) {
        return request_ == null ? org.raft.rpc.HotKeyRequest.getDefaultInstance() : request_;
      } else {
        return requestBuilder_.getMessage();
      }
    }
    /**
     * <code>.HotKeyRequest request = 2;</code>
     */
    public Builder setRequest(org.raft.rpc.HotKeyRequest value) {
      if (requestBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        request_ = value;
        onChanged();
      } else {
        requestBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.HotKeyRequest request = 2;</code>
     */
    public Builder setRequest(
        org.raft.rpc.HotKeyRequest.Builder builderForValue) {
      if (requestBuilder_ == null) {
        request_ = builderForValue.build();
        onChanged();
      } else {
        requestBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.HotKeyRequest request = 2;</code>
     */
    public Builder mergeRequest(org.raft.rpc.HotKeyRequest value) {
      if (requestBuilder_ == null) {
        if (request_ != null) {
          request_ =
            org.raft.rpc.HotKeyRequest.newBuilder(request_).mergeFrom(value).buildPartial();
        } else {
          request_ = value;
        }
        onChanged();
      } else {
        requestBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.HotKeyRequest request = 2;</code>
     */
    public Builder clearRequest() {
      if (requestBuilder_ == null) {
        request_ = null;
        onChanged();
      } else {
        request_ = null;
        requestBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.HotKeyRequest request = 2;</code>
     */
    public org.raft.rpc.HotKeyRequest.Builder getRequestBuilder() {
      
      onChanged();
      return getRequestFieldBuilder().getBuilder();
    }
    /**
     * <code>.HotKeyRequest request = 2;</code>
     */
    public org.raft.rpc.HotKeyRequestOrBuilder getRequestOrBuilder() {
      if (requestBuilder_ != null) {
        return requestBuilder_.getMessageOrBuilder();
      } else {
        return request_ == null ?
            org.raft.rpc.HotKeyRequest.getDefaultInstance() : request_;
      }
    }
    /**
     * <code>.HotKeyRequest request = 2;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        org.raft.rpc.HotKeyRequest, org.raft.rpc.HotKeyRequest.Builder, org.raft.rpc.HotKeyRequestOrBuilder> 
        getRequestFieldBuilder() {
      if (requestBuilder_ == null) {
        requestBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            org.raft.rpc.HotKeyRequest, org.raft.rpc.HotKeyRequest.Builder, org.raft.rpc.HotKeyRequestOrBuilder>(
                getRequest(),
                getParentForChildren(),
                isClean());
        request_ = null;
      }
      return requestBuilder_;
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


    // @@protoc_insertion_point(builder_scope:KeyDistributionRequest)
  }

  // @@protoc_insertion_point(class_scope:KeyDistributionRequest)
  private static final org.raft.rpc.KeyDistributionRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new org.raft.rpc.KeyDistributionRequest();
  }

  public static org.raft.rpc.KeyDistributionRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<KeyDistributionRequest>
      PARSER = new com.google.protobuf.AbstractParser<KeyDistributionRequest>() {
    @java.lang.Override
    public KeyDistributionRequest parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new KeyDistributionRequest(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<KeyDistributionRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<KeyDistributionRequest> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public org.raft.rpc.KeyDistributionRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
