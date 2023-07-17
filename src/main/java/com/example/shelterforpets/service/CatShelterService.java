package com.example.shelterforpets.service;

import com.example.shelterforpets.entity.CatShelterClient;
import com.example.shelterforpets.repository.CatShelterClientRepository;
import com.example.shelterforpets.repository.ClientRepository;
import com.example.shelterforpets.repository.VolunteerRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CatShelterService {
    private final TelegramBot telegramBot;
    private final ShelterService shelterService;
    private final VolunteerRepository volunteerRepository;
    private final ClientRepository clientRepository;
    private final CatShelterClientRepository catShelterClientRepository;

    public CatShelterService(TelegramBot telegramBot,
                             ShelterService shelterService,
                             VolunteerRepository volunteerRepository,
                             ClientRepository clientRepository,
                             CatShelterClientRepository catShelterClientRepository) {
        this.telegramBot = telegramBot;
        this.shelterService = shelterService;
        this.volunteerRepository = volunteerRepository;
        this.clientRepository = clientRepository;
        this.catShelterClientRepository = catShelterClientRepository;
    }

    public void catShelterMenu(long chatId) {
        shelterService.sendInfoShelterMenu(chatId);
    }

    public void catInfoShelterMenu(long chatId) {
        shelterService.infoShelterMenu(chatId);
    }

    public void catAdoptionInstructions(long chatId) {
        shelterService.clientAdoptionInstructionsForCatShelter(chatId);
    }

    public void report(long chatId) {
        shelterService.sendPetReport(chatId);
    }

    public void messageHelpingVolunteers(long chatId, String firstName, String userName) {
        shelterService.sendMessageHelpingVolunteers(chatId, firstName, userName);
    }

    //Информация о приюте для кошек

    /**
     * Sends cat shelter information to the specified chat ID.
     *
     * @param chatId The ID of the chat to send the message to.
     */
    public void sendCatShelterInfo(long chatId) {
        String shelterInfoText = "Приют для кошек «Мурка» \n" +
                "Адрес: г. Москва, Дмитровское шоссе\n" +
                "Телефон: +7 (999) 095-62-63\n" +
                "Email: murka@catpriut.ru";
        sendNotification(chatId, shelterInfoText);
    }

    //Вывод расписания приюта для кошек

    /**
     * Sends cat shelter information to the specified chat ID.
     *
     * @param chatId The ID of the chat to send the message to.
     */
    public void scheduleCatShelter(long chatId) {
        String scheduleCatShelter = "Информация о работе приюта:\n" +
                "Время работы: 24/7 \n" +
                "Адрес: г. Москва, Дмитровское шоссе \n";
        sendNotification(chatId, scheduleCatShelter);
    }

    //Вывод контактных данных охраны для оформления пропуска на машину в приют для кошек

    /**
     * Sends cat shelter information to the specified chat ID.
     *
     * @param chatId The ID of the chat to send the message to.
     */
    public void securityContactDetailsCatShelter(long chatId) {
        String securityContactDetailsCatShelter = "Контактные данные охраны приюта:\n" +
                "Телефон: +7 (999) 095-62-61 \n" +
                "Имя: Добрыня Никитич";
        sendNotification(chatId, securityContactDetailsCatShelter);
    }

    //Вывод общих рекомендаций о технике безопасности на территории приюта для кошек

    /**
     * Sends cat shelter information to the specified chat ID.
     *
     * @param chatId The ID of the chat to send the message to.
     */
    public void recommendationInTheCatShelter(long chatId) {
        String recommendationInTheCatShelter = "Техника безопасности:\n" +
                "1. Запрещается находиться на территории приюта без сопровождения персонала приюта. \n" +
                "2. Запрещается открывть вольеры. ";
        sendNotification(chatId, recommendationInTheCatShelter);
    }

    public void nameAndPhoneNumberPattern(long chatId) {
        shelterService.sendNameAndPhoneNumberPattern(chatId);
    }

    //Позвать волонтера приюта для кошек

    /**
     * Sends cat shelter information to the specified chat ID.
     *
     * @param chatId The ID of the chat to send the message to.
     */
    public void getHelpingCatShelterVolunteers(long chatId) {
        Random rn = new Random();
        int answer = rn.nextInt(5) + 1;
        volunteerRepository.findAllByUserId(answer); //Добавил случайный выбор волонтера
        SendMessage messageToVolunteer = new SendMessage(
                volunteerRepository.findAllByUserId(answer).getUserId(),
                "С вами хочет связаться клиент: " +
                        clientRepository.findAllByUserId(chatId).getUserId());
        telegramBot.execute(messageToVolunteer);
        SendMessage message = new SendMessage(chatId,
                "С Вами свяжется волонтер!" +
                        " Вы можете ему позвонить по указаному номеру телефона: " +
                        volunteerRepository.findAllByUserId(answer).getPhoneNumber());
        telegramBot.execute(message);
    }

    /**
     * Sends a notification message to the specified chat ID using the given text.
     *
     * @param chatId      The ID of the chat to send the notification to.
     * @param name        The text of the notification message.
     * @param phoneNumber The text of the notification message.
     */
    public void saveCatShelterClientNameAndPhoneNumber(long chatId, String name, String phoneNumber) {
        CatShelterClient client = catShelterClientRepository.findAllByUserId(chatId);
        client.setName(name);
        client.setPhoneNumber(phoneNumber);
        catShelterClientRepository.save(client);
    }


    public void sendRulesCatShelter(long chatId) {
        String shelterInfoText = "Правила знакомства с животным до того, как забрать его из приюта:\n" +
                "1. Постепенное знакомство: " +
                "Животные могут испытывать стресс и тревогу в новой среде. " +
                "Поэтому важно постепенно знакомиться с животным, предоставляя ему время на привыкание. " +
                "Не навязывайте близкое общение сразу же, дайте ему и себе время на адаптацию.\n" +
                "2. Позитивное взаимодействие: " +
                "Используйте позитивное подкрепление и поощрение, чтобы установить положительную связь с животным. " +
                "Давайте лакомства, похвалу и ласку, когда животное ведет себя хорошо. " +
                "Это поможет ему связать вас с приятными впечатлениями.\n" +
                "3. Будьте терпеливы: " +
                "Каждое животное имеет свой характер и свои потребности. " +
                "Оно может нуждаться во времени, чтобы привыкнуть к вам и новому окружению. " +
                "Будьте терпеливы и дайте ему время на установление доверительных отношений с вами.\n" +
                "4. Учитесь читать язык тела животного: " +
                "Наблюдайте за выражением лица, позой и жестами животного, чтобы понять его эмоциональное состояние и настроение. " +
                "Это поможет вам лучше понять, когда животное чувствует себя комфортно и когда нужно предоставить ему пространство.\n" +
                "5. Соблюдайте предосторожность: " +
                "В приюте могут находиться животные, которые могут быть в процессе восстановления после травм или болезни. " +
                "Будьте осторожны и уважайте их границы. " +
                "Если у вас возникают вопросы или опасения, обратитесь к сотрудникам приюта для совета и инструкций.\n" +
                "6. Обратитесь за помощью: " +
                "Если у вас есть вопросы или неуверенности в отношении определенного животного, " +
                "не стесняйтесь обратиться за помощью к сотрудникам приюта. " +
                "Они смогут предоставить вам необходимую информацию и руководство.\n" +
                "7. Важно помнить!!!  " +
                "Знакомство с животным из приюта - это важный шаг, " +
                "и ваша поддержка и любовь могут сделать разницу в жизни этого животного. " +
                "Следуя этим правилам, вы создадите благоприятную атмосферу для успешной адаптации нового питомца.\n";
        sendNotification(chatId, shelterInfoText);
    }

    public void sendDocumentsCatShelter(long chatId) {
        String shelterInfoText = "Список документов, необходимых для того, чтобы взять животное из приюта:\n" +
                "1. Заявление на усыновление\n" +
                "2. Удостоверение личности\n" +
                "3. Документы о жилище\n" +
                "4. Договор усыновления\n" +
                "5. Оплата взноса\n";
        sendNotification(chatId, shelterInfoText);
    }

    public void sendRecommendationsForTransportingCats(long chatId) {
        String shelterInfoText = "Список рекомендаций по транспортировке животного:\n" +
                "1.Используйте безопасный и комфортный транспорт: " +
                "Для перевозки животного выберите подходящий транспорт, " +
                "такой как переноска, животный бокс или специальный автомобильный переносной вольер. " +
                "Убедитесь, что он достаточно просторный, хорошо вентилируется и обеспечивает достаточно места для животного, " +
                "чтобы почувствовать себя комфортно.\n" +
                "2. Привыкание к переноске: " +
                "Предоставьте животному время на привыкание к переноске перед поездкой. " +
                "Оставьте ее в доступном месте в доме и поощряйте животное за позитивное взаимодействие с ней, например, " +
                "давайте лакомства или игрушки внутри переноски.\n" +
                "3. Зафиксируйте переноску в автомобиле: " +
                "Если вы перевозите животное в автомобиле, " +
                "убедитесь, что переноска надежно закреплена, чтобы предотвратить ее движение во время поездки. " +
                "Это можно сделать с помощью ремней безопасности или специальных креплений.\n" +
                "4. Передвигайтесь плавно: " +
                "Избегайте резких торможений, ускорений и поворотов, чтобы предотвратить потерю равновесия и дискомфорт животного. " +
                "Попытайтесь создать плавное и спокойное движение во время поездки.\n" +
                "5. Обеспечьте вентиляцию: " +
                "Убедитесь, что в переноске или в автомобиле есть достаточное количество свежего воздуха. " +
                "Обеспечьте хорошую вентиляцию, открыв окна или использовав вентиляционные отверстия в переноске.\n" +
                "6. Не оставляйте животное без присмотра: " +
                "Никогда не оставляйте животное в переноске или в автомобиле без присмотра на длительное время. " +
                "Высокие температуры или холодные погодные условия могут быть опасны " +
                "для животного, поэтому обеспечьте его надлежащий уход и присутствие.\n" +
                "7. Предоставьте воду и пищу: " +
                "Если поездка займет продолжительное время, убедитесь, " +
                "что в переноске или в автомобиле есть доступ к свежей воде и небольшому количеству еды. " +
                "Однако, будьте осторожны с кормлением животного непосредственно перед поездкой, " +
                "чтобы избежать его беспокойства или пищевых проблем.\n" +
                "8. Планируйте перерывы: " +
                "Если поездка длительная, планируйте перерывы для выгула и туалета животного. " +
                "Убедитесь, что у вас есть все необходимые принадлежности, такие как поводок, ошейник, мешок для сбора отходов и т. д.\n";
        sendNotification(chatId, shelterInfoText);
    }

    public void sendRecommendationsForImprovementsKitten(long chatId) {
        String shelterInfoText = "Список рекомендаций по обустройству дома для котенка:\n" +
                "1. Безопасность и защита:\n" +
                "Убедитесь, что дом защищен от потенциально опасных предметов или химических веществ, " +
                "которые могут быть в доступе котенка. " +
                "Поднимите химические средства, лекарства, острые или маленькие предметы, которые могут быть проглочены.\n" +
                "Закройте доступ к опасным зонам, таким как стиральная и сушильная машины, духовка, проточная вода или камины.\n" +
                "Проверьте, что окна и балконы надежно закрыты, чтобы предотвратить падение котенка.\n" +
                "2. Кормление и вода:\n" +
                "Установите плоскую миску для пищи и миску для воды в безопасном и доступном месте для котенка.\n" +
                "Используйте корм для котят, соответствующий их возрасту и потребностям. " +
                "Следуйте инструкциям по кормлению на упаковке и обратитесь к ветеринару для получения рекомендаций.\n" +
                "3. Место для отдыха и сна:\n" +
                "Предоставьте котенку уютное место для сна, такое как мягкая котячья кроватка или подушка.\n" +
                "Разместите кроватку в тихом и спокойном уголке дома, где котенок сможет отдыхать без прерываний.\n" +
                "4. Туалет и гигиена:\n" +
                "Разместите лоток для кошек в безопасном и легкодоступном месте. " +
                "Обеспечьте ему достаточно приватности, чтобы котенок чувствовал себя комфортно.\n" +
                "Используйте наполнитель, рекомендованный ветеринаром, и регулярно чистите лоток, " +
                "чтобы обеспечить гигиену котенка.\n" +
                "5. Когтеточка и игрушки:\n" +
                "Предоставьте котенку когтеточку, чтобы он мог точить свои когти и избегать повреждения мебели. " +
                "Разместите ее в доступном месте и поощряйте котенка использовать ее.\n" +
                "Обеспечьте достаточное количество игрушек для котенка, чтобы он мог заняться активностью и развлечениями. " +
                "Разнообразьте игрушки, включая мячи, мягкие игрушки, игрушки на веревке и интерактивные игрушки.\n" +
                "6. Обеспечение уровня активности:\n" +
                "Предоставьте котенку возможность заниматься физической активностью. " +
                "Разместите вертикальные платформы, лестницы, тоннели или другие игровые приспособления, " +
                "чтобы котенок мог исследовать и играть.\n" +
                "Регулярно играйте с котенком, используя интерактивные игрушки, " +
                "чтобы стимулировать его ум и физическую активность.\n" +
                "7. Ветеринарные забота и вакцинация:\n" +
                "Свяжитесь с ветеринаром, чтобы назначить первичный осмотр и рекомендации по вакцинации котенка.\n" +
                "Установите регулярные визиты к ветеринару для проверки здоровья, " +
                "профилактических прививок и контроля паразитов.\n" +
                "!!! Помните, " +
                "что каждый котенок уникален, поэтому может потребоваться индивидуальный подход к его нуждам. " +
                "Важно создать безопасное и подходящее окружение для котенка, где он будет себя комфортно и счастливо.\n";
        sendNotification(chatId, shelterInfoText);
    }

    public void sendRecommendationsForImprovementsAdultCats(long chatId) {
        String shelterInfoText = "Список рекомендаций по обустройству дома для взрослого животного:\n" +
                "1. Зона отдыха:\n" +
                "Создайте удобное место для отдыха вашего животного, такое как мягкая кровать, подушка или коврик. " +
                "Разместите его в тихом и спокойном уголке дома, где животное сможет отдыхать без прерываний.\n" +
                "2. Место для кормления:\n" +
                "Установите плоскую миску для пищи и миску для воды в безопасном и доступном месте для животного. " +
                "Обеспечьте чистоту и регулярную замену воды.\n" +
                "3. Туалет и гигиена:\n" +
                "Если ваше животное использует лоток или специальную тренировочную пеленку, убедитесь, " +
                "что они находятся в безопасном и легкодоступном месте. Регулярно чистите лоток и заменяйте наполнитель.\n" +
                "Если ваше животное ходит на улицу для нужд, создайте определенное место для этого, " +
                "используя тренировочные пеленки или песчаный ящик.\n" +
                "4. Защита от опасных предметов:\n" +
                "Поднимите химические средства, лекарства, острые или маленькие предметы, " +
                "которые могут быть вредными или вызвать проблемы при проглатывании.\n" +
                "Закройте доступ к опасным зонам, таким как стиральная и сушильная машины, " +
                "духовка, проточная вода или камины.\n" +
                "5. Когтеточка и места для игры:\n" +
                "Предоставьте животному когтеточку, чтобы оно могло точить свои когти и избегать повреждения мебели. " +
                "Разместите ее в доступном месте и поощряйте использовать ее.\n" +
                "Разместите игрушки и приспособления для игры, " +
                "чтобы ваше животное могло заниматься физической активностью и развлечениями.\n" +
                "6. Безопасное пространство и ограждения:\n" +
                "Создайте безопасное пространство в доме, где ваше животное может свободно перемещаться. " +
                "Если необходимо оградить определенные зоны или предотвратить доступ к опасным местам, " +
                "используйте ограждения или ворота.\n" +
                "7. Регулярные физические прогулки:\n" +
                "Если ваше животное нуждается в регулярных прогулках на улице, " +
                "обеспечьте ему собачий ошейник или снаряжение и поводок. " +
                "Найдите безопасный маршрут для прогулок и следуйте правилам безопасности.\n" +
                "8. Ветеринарная забота и уход:\n" +
                "Установите регулярные визиты к ветеринару для проверки здоровья и профилактических осмотров.\n" +
                "Обеспечьте регулярный уход за шерстью, когтями и зубами вашего животного, " +
                "чтобы поддерживать его здоровье и гигиену.\n" +
                "9. Общение и забота:\n" +
                "Уделяйте достаточно времени вашему животному для общения, игр и ласки. " +
                "Проводите качественное время вместе, чтобы поддерживать эмоциональную связь и благополучие.\n" +
                "10. Правильное питание:\n" +
                "Питайте ваше животное сбалансированным и качественным кормом, соответствующим его потребностям. " +
                "Следуйте рекомендациям ветеринара относительно порции и режима кормления.\n" +
                "!!! Важно помнить, " +
                "что каждое животное индивидуально, и его потребности могут различаться. " +
                "Следуйте рекомендациям ветеринара и обращайтесь к профессионалам для получения индивидуальных советов, " +
                "чтобы обеспечить безопасность и благополучие вашего питомца.\n";
        sendNotification(chatId, shelterInfoText);
    }

    public void sendRecommendationsForImprovementsAdultCatsWithDisabilities(long chatId) {
        String shelterInfoText = "Список рекомендаций по обустройству дома для животного с " +
                "ограниченными возможностями (зрение, передвижение):\n" +
                "1. Безопасность и пространство:\n" +
                "Убедитесь, что дом защищен от потенциально опасных предметов, которые могут стать преградой для животного. " +
                "Поднимите химические средства, острые предметы или маленькие предметы, которые могут быть проглочены.\n" +
                "Разместите мебель и другие предметы таким образом, " +
                "чтобы создать просторное и безопасное пространство для передвижения животного.\n" +
                "2. Помощь в передвижении:\n" +
                "Если ваше животное имеет ограниченные возможности передвижения, убедитесь, " +
                "что в доме есть поддержка или опорные поверхности, такие как рельсы, подушки или специальные пандусы, " +
                "которые помогут ему легче перемещаться по дому.\n" +
                "Удалите препятствия с пути передвижения животного, чтобы обеспечить ему свободный проход.\n" +
                "3. Отметки и контрастные цвета:\n" +
                "Используйте контрастные цвета, чтобы обозначить различные области дома или предметы, " +
                "которые могут быть полезны для животного с ограниченным зрением. " +
                "Например, выделите кормушку или лоток для кошек ярким цветом или используйте специальные отметки " +
                "для указания опасных зон.\n" +
                "Разместите мягкие подушки или другие отметки на краю или углах мебели, " +
                "чтобы помочь животному избежать столкновений.\n" +
                "4. Информационные звуки:\n" +
                "Если ваше животное имеет ограниченное зрение, можно использовать информационные звуки, " +
                "чтобы помочь ему ориентироваться в доме. " +
                "Например, вы можете использовать колокольчики на ошейнике или привязать подвижные игрушки с звуковыми элементами.\n" +
                "5. Постепенные изменения:\n" +
                "При внесении изменений в дом, делайте это постепенно, чтобы животное могло привыкнуть к новым условиям. " +
                "Помещайте новые предметы или переставляйте мебель осторожно, предоставляя достаточно времени для адаптации.\n" +
                "6. Регулярное обследование:\n" +
                "Регулярно проверяйте состояние дома и преграды, чтобы убедиться, что они не представляют опасности для животного. " +
                "Проверяйте наличие острых углов, выступающих предметов или преград, которые могут привести к травмам.\n" +
                "7. Ветеринарная забота:\n" +
                "Свяжитесь с ветеринаром, чтобы получить рекомендации и советы по уходу за животным с ограниченными возможностями. " +
                "Ветеринар сможет предложить индивидуальные рекомендации, учитывая конкретные потребности вашего питомца.\n" +
                " Важно помнить, что каждое животное с ограниченными возможностями уникально, " +
                "и его потребности могут различаться. " +
                "Обращайтесь к профессионалам и ветеринару для получения индивидуальных советов, " +
                "чтобы обеспечить безопасность и комфорт вашего питомца.\n";
        sendNotification(chatId, shelterInfoText);
    }

    /**
     * Sends a notification message to the specified chat ID using the given text.
     * * @param chatId The ID of the chat to send the notification to.
     *
     * @param text The text of the notification message.
     */
    private void sendNotification(long chatId, String text) {
        SendMessage message = new SendMessage(chatId, text);
        telegramBot.execute(message);
    }


    //
    //
    // Methods for RestController
    //
    //
    /**
     * Find client by id from cat shelter repository
     *
     * @param userId The ID of the user ID in repository
     * @return Object CatShelter
     */
    public CatShelterClient findClientFromCatShelter(long userId) {
        return catShelterClientRepository
                .findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Клиента нет в базе данных!"));
    }

    /**
     * Post client by id from cat shelter repository
     *
     * @param userId The ID of the user ID in repository
     * @return Object CatShelter
     */
    public CatShelterClient postClientFromCatShelter(long userId, CatShelterClient catShelterClient) {
        if (catShelterClientRepository.existsAllByUserId(userId) == null) {
            catShelterClient.setUserId(userId);
            return catShelterClientRepository.save(catShelterClient);
        }
        return null;
    }


    /**
     * Edit client by id in cat shelter repository
     *
     * @param catShelterClient The client from cat shelter
     * @return Object CatShelter
     */
    public CatShelterClient putClientFromCatShelter(long userId, CatShelterClient catShelterClient) {
        if (catShelterClientRepository.existsAllByUserId(userId) != null) {
            catShelterClient.setUserId(userId);
            return catShelterClientRepository.save(catShelterClient);
        }
        return null;
    }

    /**
     * Delete client by id in cat shelter repository
     *
     * @param userId The ID of the user ID in repository
     */
    public void deleteClientFromCatShelter(long userId) {
        if (catShelterClientRepository.existsAllByUserId(userId)) {
            catShelterClientRepository.deleteById(userId);
        }
    }


}
