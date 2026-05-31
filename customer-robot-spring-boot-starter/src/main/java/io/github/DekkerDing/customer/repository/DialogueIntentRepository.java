package io.github.DekkerDing.customer.repository;

import io.github.DekkerDing.customer.domain.dialogue.DialogueIntent;
import java.util.List;
import java.util.Optional;

public interface DialogueIntentRepository {
    Optional<DialogueIntent> findById(Long id);
    Optional<DialogueIntent> findByIntentCode(String intentCode);
    List<DialogueIntent> findByActiveStatus();
    List<DialogueIntent> findAll();
    DialogueIntent save(DialogueIntent intent);
}
