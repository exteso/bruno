package com.exteso.bruno.model;

import static java.util.EnumSet.allOf;
import static java.util.EnumSet.of;

import java.util.Set;

//https://github.com/exteso/bruno/issues/3 issues
public enum FailureType {

    // fognatura esterna intasata
    CLOGGED_SEWER(of(CompanyType.CONSTRUCTION_COMPANY)),

    // scasso porta
    BROKEN_DOOR(of(CompanyType.CARPENTER)), //
    // rottura serramento
    BROKEN_WINDOW_FRAME(of(CompanyType.CARPENTER)), //
    //
    OTHER_CARPENTER(of(CompanyType.CARPENTER)),

    // infiltrazione acqua tetto
    WATER_INFILTRATION_ROOF(of(CompanyType.WATERPROOFING_COMPANY)), //
    // infiltrazione acqua autorimessa o facciata esterna
    WATER_INFILTRATION_GARAGE_OR_EXTERNAL_FACADE(of(CompanyType.WATERPROOFING_COMPANY)), //
    //
    OTHER_WATERPROOFING_COMPANY(of(CompanyType.WATERPROOFING_COMPANY)),

    // rottura tubo interno,
    BROKEN_INTERNAL_TUBE(of(CompanyType.PLUMBER)),
    // acqua calda che non arriva,
    NO_HOT_WATER(of(CompanyType.PLUMBER)),
    // serpentine/riscaldamento non funzionanti;
    COILS_HEATING_ISSUE(of(CompanyType.PLUMBER)),
    //
    OTHER_PLUMBER(of(CompanyType.PLUMBER)),

    // luci giardino non funzionanti,
    GARDEN_LIGHTS_ISSUE(of(CompanyType.ELECTRICIAN)),
    // presa camera non funzionante,
    ROOM_SOCKET_ISSUE(of(CompanyType.ELECTRICIAN)),
    // interruttore cantina bloccato;
    CELLAR_SWITCH_ISSUE(of(CompanyType.ELECTRICIAN)),
    //
    OTHER_ELECTRICIAN(of(CompanyType.ELECTRICIAN)),
    //

    OTHER(allOf(CompanyType.class));

    private final Set<CompanyType> relatedTo;

    private FailureType(Set<CompanyType> relatedTo) {
        this.relatedTo = relatedTo;
    }

    public Set<CompanyType> getRelatedTo() {
        return relatedTo;
    }
}
