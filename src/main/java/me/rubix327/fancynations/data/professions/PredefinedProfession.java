package me.rubix327.fancynations.data.professions;

import lombok.Getter;

import java.util.HashMap;

public enum PredefinedProfession {

    Mayor,
    Helper;

    @Getter
    private static final HashMap<PredefinedProfession, String> locales = new HashMap<>();

}