package Ontdekstation013.ClimateChecker.repositories;

import Ontdekstation013.ClimateChecker.models.Token;
import Ontdekstation013.ClimateChecker.models.Translation;
import Ontdekstation013.ClimateChecker.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TranslationRepository extends JpaRepository<Translation, Integer> {
}
