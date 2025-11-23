package library.bot.components.service;

import library.bot.components.repository.RepositoryComponent;
import library.bot.DiaryService.DiaryService;
import library.bot.DiaryService.impl.DiaryServiceImpl;

public class ServiceComponent {
    private final DiaryService diaryService;

    public ServiceComponent(RepositoryComponent repositoryComponent)
    {
        this.diaryService = new DiaryServiceImpl(repositoryComponent.getBookRepository(),
                repositoryComponent.getUserBookMetadataRepository(), repositoryComponent.getUserRepository(),
                repositoryComponent.getAuthorRepository());
    }

    public DiaryService getDiaryService()
    {
        return diaryService;
    }
}
