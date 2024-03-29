package com.example.pubsync.service;

import com.example.pubsync.entity.Author;
import com.example.pubsync.entity.Publication;
import com.example.pubsync.repository.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for filtering publications based on specific criteria.
 * Provides functionality to filter publications by year and to identify new publications.
 */
@Service
public class FilterService {

    @Autowired
    private PublicationRepository publicationRepository;

    /**
     * Filters a list of publications to include only those that are not already in the database.
     * @param databankPublications The list of publications to be filtered.
     * @return A list of filtered publications.
     */
    public List<Publication> filterAndSaveNewPublications(List<Publication> databankPublications){
        List<Publication> databasePublications = publicationRepository.findAll();
        List<Publication> filteredList = new ArrayList<>();

        for(Publication databankPublication : databankPublications) {
            boolean isExist = false;
            for (Publication databasePublication : databasePublications){
                if (databankPublication.getPublishLink().equals(databasePublication.getPublishLink())){
                    isExist = true;
                    break;
                }

            }
                if(isExist){
                    continue;
            }
            filteredList.add(databankPublication);
        }
        return filteredList;
    }

    /**
     * Filters a list of publications to include only those that were published within the author's start and quit dates.
     * @param publications The list of publications to be filtered.
     * @param author The author whose start and quit dates are used for filtering.
     * @return A list of filtered publications.
     */
    public List<Publication> filterByYear(List<Publication> publications, Author author){

        List<Publication> filteredList = new ArrayList<>();

        int startYear = Integer.parseInt(author.getStartDate());
        int currentYear = Year.now().getValue();

        int endYear = (author.getQuitDate() == null || author.getQuitDate().isEmpty()) ?
                currentYear : Integer.parseInt(author.getQuitDate());

        for (Publication publication : publications) {
            int publicationYear = Integer.parseInt(publication.getYear());
            if (startYear <= publicationYear && publicationYear <= endYear) {
                filteredList.add(publication);
            }
        }

        return filteredList;
    }


}
