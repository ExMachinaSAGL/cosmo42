package ch.exmachina.cosmo42.entities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import ch.exmachina.cosmo42.services.kb.schema.ChunkType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class KBDocumentChunkTypeTest {

    @Test
    void fromLabelReturnsTextForText() {
        assertThat(ChunkType.fromLabel("text")).isEqualTo(ChunkType.TEXT);
    }

    @Test
    void fromLabelReturnsTableForTable() {
        assertThat(ChunkType.fromLabel("table")).isEqualTo(ChunkType.TABLE);
    }

    @Test
    void fromLabelReturnsImageForImage() {
        assertThat(ChunkType.fromLabel("image")).isEqualTo(ChunkType.IMAGE);
    }

    @Test
    void fromLabelIsCaseSensitive() {
    	assertThatThrownBy(() -> ChunkType.fromLabel("TEXT")).isExactlyInstanceOf(IllegalArgumentException.class);
    	assertThatThrownBy(() -> ChunkType.fromLabel("Table")).isExactlyInstanceOf(IllegalArgumentException.class);
    	assertThatThrownBy(() -> ChunkType.fromLabel("IMAGE")).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "video", "unknown", "pdf", "  text  "})
    void fromLabelFailsForUnknownValues(String label) {
    	assertThatThrownBy(() -> ChunkType.fromLabel(label)).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @NullSource
    void fromLabelReturnsFallbackForNull(String label) {
    	assertThatThrownBy(() -> ChunkType.fromLabel(label)).isExactlyInstanceOf(IllegalArgumentException.class);
    }
}
