package com.example.pubsync.service;

import com.example.pubsync.model.PublicationView;
import com.example.pubsync.repository.PublicationRepository;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.*;

@Service
public class FileService {

    private final PublicationRepository publicationRepository;
    private final ConverterService converterService;

    public FileService(PublicationRepository publicationRepository, ConverterService converterService) {
        this.publicationRepository = publicationRepository;
        this.converterService = converterService;
    }

    public void createMarkdownFile() throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter("/Users/emrealma/Desktop/testwebrepo-main/content/research/publications/_index.md")) {
            List<PublicationView> publicationViewList = converterService.convertPublicationViewList(publicationRepository.findAll());

            writer.println("---");
            writer.println("title: \"Publications\"");
            writer.println("bg_image: \"images/2020-landscape-2.png\"");
            writer.println("description: \"This is meta description\"");
            writer.println("draft: false");
            writer.println("weight: 2");
            writer.println("---");
            writer.println();
            writer.println("{{< rawhtml >}}");
            writer.println("  <p class=\"speshal-fancy-custom\">");
            writer.println("  </p>");
            writer.println("{{< /rawhtml >}}");
            writer.println();

            Map<Integer, List<PublicationView>> publicationsByYear = new TreeMap<>(Comparator.reverseOrder());
            for (PublicationView publication : publicationViewList) {
                publicationsByYear.computeIfAbsent(Integer.valueOf(publication.getYear()), k -> new ArrayList<>()).add(publication);
            }

            for (Map.Entry<Integer, List<PublicationView>> entry : publicationsByYear.entrySet()) {
                writer.println("## " + entry.getKey());
                for (PublicationView publication : entry.getValue()) {
                    writer.println();
                    writer.println("- " + publication.getPublishAuthors() + ". ");
                    writer.println(publication.getTitle() + ". _" + publication.getVenue() + ", " + publication.getYear() + "_.  ");
                    if(publication.getPublishLink().startsWith("http")) {
                        writer.println("[[Link]](" + publication.getPublishLink() + ") ");
                    } else {
                        writer.println("[[Link]](/link/" + publication.getPublishLink() + ") ");
                    }
                    writer.println();
                }
            }
        }
    }
}