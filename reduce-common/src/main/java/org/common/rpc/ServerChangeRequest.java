// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: server.proto

package org.common.rpc;

/**
 * Protobuf type {@code jraft.ServerChangeRequest}
 */
public  final class ServerChangeRequest extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:jraft.ServerChangeRequest)
    ServerChangeRequestOrBuilder {
private static final long serialVersionUID = 0L;
  // Use ServerChangeRequest.newBuilder() to construct.
  private ServerChangeRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private ServerChangeRequest() {
    servers_ = com.google.protobuf.LazyStringArrayList.EMPTY;
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new ServerChangeRequest();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private ServerChangeRequest(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    int mutable_bitField0_ = 0;
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
            if (!((mutable_bitField0_ & 0x00000001) != 0)) {
              servers_ = new com.google.protobuf.LazyStringArrayList();
              mutable_bitField0_ |= 0x00000001;
            }
            servers_.add(s);
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
      if (((mutable_bitField0_ & 0x00000001) != 0)) {
        servers_ = servers_.getUnmodifiableView();
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return org.common.rpc.Server.internal_static_jraft_ServerChangeRequest_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return org.common.rpc.Server.internal_static_jraft_ServerChangeRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            org.common.rpc.ServerChangeRequest.class, org.common.rpc.ServerChangeRequest.Builder.class);
  }

  public static final int SERVERS_FIELD_NUMBER = 1;
  private com.google.protobuf.LazyStringList servers_;
  /**
   * <pre>
   *change后的server节点
   * </pre>
   *
   * <code>repeated string servers = 1;</code>
   */
  public com.google.protobuf.ProtocolStringList
      getServersList() {
    return servers_;
  }
  /**
   * <pre>
   *change后的server节点
   * </pre>
   *
   * <code>repeated string servers = 1;</code>
   */
  public int getServersCount() {
    return servers_.size();
  }
  /**
   * <pre>
   *change后的server节点
   * </pre>
   *
   * <code>repeated string servers = 1;</code>
   */
  public java.lang.String getServers(int index) {
    return servers_.get(index);
  }
  /**
   * <pre>
   *change后的server节点
   * </pre>
   *
   * <code>repeated string servers = 1;</code>
   */
  public com.google.protobuf.ByteString
      getServersBytes(int index) {
    return servers_.getByteString(index);
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
    for (int i = 0; i < servers_.size(); i++) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, servers_.getRaw(i));
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    {
      int dataSize = 0;
      for (int i = 0; i < servers_.size(); i++) {
        dataSize += computeStringSizeNoTag(servers_.getRaw(i));
      }
      size += dataSize;
      size += 1 * getServersList().size();
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
    if (!(obj instanceof org.common.rpc.ServerChangeRequest)) {
      return super.equals(obj);
    }
    org.common.rpc.ServerChangeRequest other = (org.common.rpc.ServerChangeRequest) obj;

    if (!getServersList()
        .equals(other.getServersList())) return false;
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
    if (getServersCount() > 0) {
      hash = (37 * hash) + SERVERS_FIELD_NUMBER;
      hash = (53 * hash) + getServersList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static org.common.rpc.ServerChangeRequest parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.common.rpc.ServerChangeRequest parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.common.rpc.ServerChangeRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.common.rpc.ServerChangeRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.common.rpc.ServerChangeRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.common.rpc.ServerChangeRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.common.rpc.ServerChangeRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.common.rpc.ServerChangeRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.common.rpc.ServerChangeRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static org.common.rpc.ServerChangeRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.common.rpc.ServerChangeRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.common.rpc.ServerChangeRequest parseFrom(
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
  public static Builder newBuilder(org.common.rpc.ServerChangeRequest prototype) {
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
   * Protobuf type {@code jraft.ServerChangeRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:jraft.ServerChangeRequest)
      org.common.rpc.ServerChangeRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return org.common.rpc.Server.internal_static_jraft_ServerChangeRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return org.common.rpc.Server.internal_static_jraft_ServerChangeRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              org.common.rpc.ServerChangeRequest.class, org.common.rpc.ServerChangeRequest.Builder.class);
    }

    // Construct using org.common.rpc.ServerChangeRequest.newBuilder()
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
      servers_ = com.google.protobuf.LazyStringArrayList.EMPTY;
      bitField0_ = (bitField0_ & ~0x00000001);
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return org.common.rpc.Server.internal_static_jraft_ServerChangeRequest_descriptor;
    }

    @java.lang.Override
    public org.common.rpc.ServerChangeRequest getDefaultInstanceForType() {
      return org.common.rpc.ServerChangeRequest.getDefaultInstance();
    }

    @java.lang.Override
    public org.common.rpc.ServerChangeRequest build() {
      org.common.rpc.ServerChangeRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public org.common.rpc.ServerChangeRequest buildPartial() {
      org.common.rpc.ServerChangeRequest result = new org.common.rpc.ServerChangeRequest(this);
      int from_bitField0_ = bitField0_;
      if (((bitField0_ & 0x00000001) != 0)) {
        servers_ = servers_.getUnmodifiableView();
        bitField0_ = (bitField0_ & ~0x00000001);
      }
      result.servers_ = servers_;
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
      if (other instanceof org.common.rpc.ServerChangeRequest) {
        return mergeFrom((org.common.rpc.ServerChangeRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(org.common.rpc.ServerChangeRequest other) {
      if (other == org.common.rpc.ServerChangeRequest.getDefaultInstance()) return this;
      if (!other.servers_.isEmpty()) {
        if (servers_.isEmpty()) {
          servers_ = other.servers_;
          bitField0_ = (bitField0_ & ~0x00000001);
        } else {
          ensureServersIsMutable();
          servers_.addAll(other.servers_);
        }
        onChanged();
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
      org.common.rpc.ServerChangeRequest parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (org.common.rpc.ServerChangeRequest) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private com.google.protobuf.LazyStringList servers_ = com.google.protobuf.LazyStringArrayList.EMPTY;
    private void ensureServersIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        servers_ = new com.google.protobuf.LazyStringArrayList(servers_);
        bitField0_ |= 0x00000001;
       }
    }
    /**
     * <pre>
     *change后的server节点
     * </pre>
     *
     * <code>repeated string servers = 1;</code>
     */
    public com.google.protobuf.ProtocolStringList
        getServersList() {
      return servers_.getUnmodifiableView();
    }
    /**
     * <pre>
     *change后的server节点
     * </pre>
     *
     * <code>repeated string servers = 1;</code>
     */
    public int getServersCount() {
      return servers_.size();
    }
    /**
     * <pre>
     *change后的server节点
     * </pre>
     *
     * <code>repeated string servers = 1;</code>
     */
    public java.lang.String getServers(int index) {
      return servers_.get(index);
    }
    /**
     * <pre>
     *change后的server节点
     * </pre>
     *
     * <code>repeated string servers = 1;</code>
     */
    public com.google.protobuf.ByteString
        getServersBytes(int index) {
      return servers_.getByteString(index);
    }
    /**
     * <pre>
     *change后的server节点
     * </pre>
     *
     * <code>repeated string servers = 1;</code>
     */
    public Builder setServers(
        int index, java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  ensureServersIsMutable();
      servers_.set(index, value);
      onChanged();
      return this;
    }
    /**
     * <pre>
     *change后的server节点
     * </pre>
     *
     * <code>repeated string servers = 1;</code>
     */
    public Builder addServers(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  ensureServersIsMutable();
      servers_.add(value);
      onChanged();
      return this;
    }
    /**
     * <pre>
     *change后的server节点
     * </pre>
     *
     * <code>repeated string servers = 1;</code>
     */
    public Builder addAllServers(
        java.lang.Iterable<java.lang.String> values) {
      ensureServersIsMutable();
      com.google.protobuf.AbstractMessageLite.Builder.addAll(
          values, servers_);
      onChanged();
      return this;
    }
    /**
     * <pre>
     *change后的server节点
     * </pre>
     *
     * <code>repeated string servers = 1;</code>
     */
    public Builder clearServers() {
      servers_ = com.google.protobuf.LazyStringArrayList.EMPTY;
      bitField0_ = (bitField0_ & ~0x00000001);
      onChanged();
      return this;
    }
    /**
     * <pre>
     *change后的server节点
     * </pre>
     *
     * <code>repeated string servers = 1;</code>
     */
    public Builder addServersBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      ensureServersIsMutable();
      servers_.add(value);
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


    // @@protoc_insertion_point(builder_scope:jraft.ServerChangeRequest)
  }

  // @@protoc_insertion_point(class_scope:jraft.ServerChangeRequest)
  private static final org.common.rpc.ServerChangeRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new org.common.rpc.ServerChangeRequest();
  }

  public static org.common.rpc.ServerChangeRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<ServerChangeRequest>
      PARSER = new com.google.protobuf.AbstractParser<ServerChangeRequest>() {
    @java.lang.Override
    public ServerChangeRequest parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new ServerChangeRequest(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<ServerChangeRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<ServerChangeRequest> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public org.common.rpc.ServerChangeRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
