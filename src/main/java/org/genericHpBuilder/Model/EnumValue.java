package org.genericHpBuilder.Model;

/**
 * An EnumValue is a an enum wrapper that makes handling enums easier.
 */
public interface EnumValue {

    String asString();

    EnumValue[] getAllValues();

}