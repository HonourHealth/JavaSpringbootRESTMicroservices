// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: user-service.proto

// Protobuf Java Version: 3.25.1
package com.example.user;

public interface UserInformationOrBuilder extends
    // @@protoc_insertion_point(interface_extends:user.UserInformation)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>int32 user_id = 1;</code>
   * @return The userId.
   */
  int getUserId();

  /**
   * <code>string name = 2;</code>
   * @return The name.
   */
  java.lang.String getName();
  /**
   * <code>string name = 2;</code>
   * @return The bytes for name.
   */
  com.google.protobuf.ByteString
      getNameBytes();

  /**
   * <code>int32 balance = 3;</code>
   * @return The balance.
   */
  int getBalance();

  /**
   * <code>repeated .user.Holding holdings = 4;</code>
   */
  java.util.List<com.example.user.Holding> 
      getHoldingsList();
  /**
   * <code>repeated .user.Holding holdings = 4;</code>
   */
  com.example.user.Holding getHoldings(int index);
  /**
   * <code>repeated .user.Holding holdings = 4;</code>
   */
  int getHoldingsCount();
  /**
   * <code>repeated .user.Holding holdings = 4;</code>
   */
  java.util.List<? extends com.example.user.HoldingOrBuilder> 
      getHoldingsOrBuilderList();
  /**
   * <code>repeated .user.Holding holdings = 4;</code>
   */
  com.example.user.HoldingOrBuilder getHoldingsOrBuilder(
      int index);
}
