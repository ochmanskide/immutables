/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package de.ochmanski.immutables;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class LibraryTest
{

    @Test
    void someLibraryMethodReturnsTrue()
    {
        ImmutableList classUnderTest = ImmutableList.builder().build();
        Assertions.assertThat(classUnderTest).isNotNull();
    }

}
