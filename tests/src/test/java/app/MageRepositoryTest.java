package app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class MageRepositoryTest {
    private MageRepository mageRepository;

    private final String nonexistentName = "There does not exist a mage with this name";
    private final String existentName = "TestMage";
    private final Mage existentMage = new Mage(existentName, 10);

    @BeforeEach
    void createTestCollection(){
        Collection<Mage> testCollection = new HashSet();
        testCollection.add(existentMage);
        mageRepository = new MageRepository(testCollection);
    }

    @Test
    public void deleteNonexistentMageThrows() {
        assertThatThrownBy(() -> mageRepository.delete(nonexistentName)).hasMessage("Mage '"
                + nonexistentName + "' does not exist.");
    }

    @Test
    public void findNonexistentMageReturnsEmptyOptional() {
        assertThat(mageRepository.find(nonexistentName)).isEqualTo(Optional.empty());
    }

    @Test
    public void findExistentMageReturnsNotEmptyOptional() {
        assertThat(mageRepository.find(existentName)).isNotEqualTo(Optional.empty());
    }

    @Test
    public void saveExistentMageThrows() {
        assertThatThrownBy(() -> mageRepository.save(existentMage)).hasMessage("Mage '"
                + existentMage.getName() + "' does already exist.");
    }
}
