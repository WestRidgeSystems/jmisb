package org.jmisb.api.common;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Specifies that a class or method may undergo breaking changes in a future release, even a minor
 * or patch revision. It is normally OK for client applications to depend on beta APIs, but projects
 * requiring a strictly stable API should avoid doing so.
 */
@Target({
    ElementType.ANNOTATION_TYPE,
    ElementType.CONSTRUCTOR,
    ElementType.FIELD,
    ElementType.METHOD,
    ElementType.TYPE
})
@Documented
public @interface Beta {}
