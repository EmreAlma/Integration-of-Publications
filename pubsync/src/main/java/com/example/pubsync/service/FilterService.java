package com.example.pubsync.service;

import com.example.pubsync.entity.Author;
import com.example.pubsync.entity.Publication;
import com.example.pubsync.repository.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FilterService {

    @Autowired
    private PublicationRepository publicationRepository;

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

    public List<Publication> filterByYear(List<Publication> publications, Author author){

        List<Publication> filteredList = new ArrayList<>();

        for (Publication publication : publications) {
            if (Integer.parseInt(author.getStartDate()) <= Integer.parseInt(publication.getYear()) && Integer.parseInt(publication.getYear()) <= Integer.parseInt(author.getQuitDate())) {
                filteredList.add(publication);
            }
        }

        return filteredList;
    }


}