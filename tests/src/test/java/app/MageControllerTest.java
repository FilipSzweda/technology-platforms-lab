package app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

public class MageControllerTest {
    private MageRepository mageRepository;
    private MageController mageController;

    @BeforeEach
    public void init(){
        mageRepository = mock(MageRepository.class);
        mageController = new MageController(mageRepository);
    }

    @Test
    public void testDeleteFoundMage(){
        String mageName = "name";
        assertThat(mageController.delete(mageName)).isEqualTo("done");
    }

    @Test
    public void testDeleteNotFoundMage() {
        String mageName = "bad name";
        doThrow(new IllegalArgumentException("Mage " + mageName + " not found")).when(mageRepository).delete(mageName);
        assertThat(mageController.delete(mageName)).isEqualTo("not found");
    }

    @Test
    public void testFindFoundMage(){
        String mageName = "name";
        Mage mage = new Mage(mageName, 3);
        doReturn(Optional.of(mage)).when(mageRepository).find(mageName);
        assertThat(mageController.find(mageName)).isEqualTo(mage.toString());
    }

    @Test
    public void testFindNotFoundMage(){
        String mageName = "bad name";
        doReturn(Optional.empty()).when(mageRepository).find(mageName);
        assertThat(mageController.find(mageName)).isEqualTo("not found");
    }

    @Test
    public void testSaveMageWithExistingKey(){
        String mageName = "existing name";
        Mage mage = new Mage(mageName, 1);
        doThrow(new IllegalArgumentException("Mage with name " + mage.getName()
                + " already exist")).when(mageRepository).save(any(Mage.class));
        DTO dto = new DTO();
        dto.name = mage.getName();
        dto.level = mage.getLevel();
        assertThat(mageController.save(dto)).isEqualTo("bad request");
    }

    @Test
    public void testSaveMageWithNonExistingKey(){
        String mageName = "name";
        DTO dto = new DTO();
        dto.name = mageName;
        dto.level = 3;
        assertThat(mageController.save(dto)).isEqualTo("done");
    }
}
