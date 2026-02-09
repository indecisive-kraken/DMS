//package com.dms.model;
//
//import jakarta.persistence.*;
//import lombok.*;
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.HashSet;
//import java.util.Set;
//
//
//@Slf4j
//@Entity
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
//@Table(name = "ai_urls")
//public class AIUrl extends AbstractEntity{
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long ai_sid_u;
//
//    private String ai_url;
//
//    @Getter(AccessLevel.PRIVATE) //Check if this will cause problems
//    @OneToOne(mappedBy = "sid_u", fetch = FetchType.LAZY)
//    private Set<AIUrl> ai_urls = new HashSet<>();
//
//
//    public void addAIUrl(Set<AIUrl> ai_urls) {
//
//        ai_urls.add();
//        ai_urls.add();
//        ai_urls.add();
//        ai_urls.add();
//        ai_urls.add();
//    }
//
//}
