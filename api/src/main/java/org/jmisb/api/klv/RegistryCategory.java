package org.jmisb.api.klv;

/**
 * Identifies the category of a registry as specified by a Universal Label (UL)
 */
public enum RegistryCategory
{
    /** Definitions of individual data elements */
    DICTIONARIES,

    /** Definitions of sets (groups) of data elements, such as a Set or a Pack */
    GROUPS,

    /** Definitions of frameworks for collections of information */
    WRAPPERS_AND_CONTAINERS,

    /** Definitions of descriptions that augment ULs */
    LABELS;
}
