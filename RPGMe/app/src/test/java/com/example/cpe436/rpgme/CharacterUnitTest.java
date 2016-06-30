package com.example.cpe436.rpgme;

import com.example.cpe436.rpgme.model.Character;

import org.junit.Test;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class CharacterUnitTest {

    @Test
    public void testConstructor() throws Exception {

        // This should return default character
        Character mCharacter = Character.getCharacterInstance(null);
    }
}