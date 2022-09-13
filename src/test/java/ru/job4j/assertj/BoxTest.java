package ru.job4j.assertj;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;

class BoxTest {
    @Test
    void isThisSphere() {
        Box box = new Box(0, 10);
        String name = box.whatsThis();
        assertThat(name).isEqualTo("Sphere")
                .isNotEqualToIgnoringCase("Tetrahedron");
    }

    @Test
    void isThisTetrahedron() {
        Box box = new Box(4, 40);
        String name = box.whatsThis();
        assertThat(name).isEqualTo("Tetrahedron")
                .isNotEqualToIgnoringCase("sphere")
                .isNotEqualToIgnoringCase("cube")
                .endsWithIgnoringCase("ron")
                .containsIgnoringCase("tetra");
    }

    @Test
    void isThisCube() {
        Box box = new Box(8, 256);
        String name = box.whatsThis();
        assertThat(name).isEqualTo("Cube")
                .isNotEqualToIgnoringCase("tetrahedron")
                .isNotEqualToIgnoringCase("sphere")
                .endsWithIgnoringCase("be")
                .containsIgnoringCase("ub");
    }

    @Test
    void isThisUnknownObject() {
        Box box = new Box(18, 256);
        String name = box.whatsThis();
        assertThat(name).isEqualTo("Unknown object")
                .isNotEmpty()
                .doesNotContain("Sphere")
                .doesNotContain("Tetrahedron")
                .doesNotContain("Cube");
    }

    @Test
    void isCubeHave8Vertex() {
        Box box = new Box(8, 64);
        int vertex = box.getNumberOfVertices();
        assertThat(vertex).isNotNull()
                .isNotZero()
                .isPositive()
                .isGreaterThan(7)
                .isLessThan(9)
                .isEqualTo(8);
    }

    @Test
    void isTetrahedronHave4Vertex() {
        Box box = new Box(4, 16);
        int vertex = box.getNumberOfVertices();
        assertThat(vertex).isNotNull()
                .isNotZero()
                .isPositive()
                .isGreaterThan(3)
                .isLessThan(5)
                .isEqualTo(4);
    }

    @Test
    void cubeHaveVertexLength5ThenAreaEqualsTo150() {
        Box box = new Box(8, 5);
        double result = box.getArea();
        assertThat(result).isNotNull()
                .isNotZero()
                .isPositive()
                .isGreaterThan(149d)
                .isLessThan(151d)
                .isEqualTo(150d);
    }

    @Test
    void tetrahedronHaveVertexLength5ThenAreaEqualsTo43() {
        Box box = new Box(4, 5);
        double result = box.getArea();
        assertThat(result).isNotNull()
                .isNotZero()
                .isPositive()
                .isGreaterThan(43d)
                .isLessThan(44d)
                .isCloseTo(43.3d, withPrecision(0.01d));
    }

    @Test
    void cubeHaveExist() {
        Box box = new Box(8, 64);
        boolean result = box.isExist();
        assertThat(result).isNotNull()
                .isTrue();
    }

    @Test
    void nknownObjectNotExist() {
        Box box = new Box(21, 64);
        boolean result = box.isExist();
        assertThat(result).isNotNull()
                .isFalse();
    }
}
