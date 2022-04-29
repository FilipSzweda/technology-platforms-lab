package app;

import java.util.Optional;

public class MageController {
    private MageRepository repository;

    public MageController(MageRepository repository) {
        this.repository = repository;
    }

    public String find(String name) {
        Optional<Mage> found = this.repository.find(name);
        return found.isEmpty() ? "not found" : found.get().toString();
    }

    public String delete(String name) {
        try {
            this.repository.delete(name);
        } catch(IllegalArgumentException ex) {
            return "not found";
        }
        return "done";
    }

    public String save(DTO dto) {
        try {
            this.repository.save(new Mage(dto.name, dto.level));
        } catch(IllegalArgumentException ex) {
            return "bad request";
        }
        return "done";
    }
}
