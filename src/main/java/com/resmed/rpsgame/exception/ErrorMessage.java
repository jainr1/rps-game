package com.resmed.rpsgame.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class ErrorMessage {

  private int statusCode;
  private LocalDateTime timestamp;
  private String shortMessage;
  private String description;

}