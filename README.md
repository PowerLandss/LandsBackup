# LandsBackup
Best backup's in rumine (MayBe)

configuration:
settings:
  server: "grief"
  type: "plugins" #all/plugins #Первое сохраняет все с ядром, второе сохраняет только плагины
  onStop: false #Сохранять сборку при выключении сервера
  task:
    enabled: true
    time: 18000 #Время в секундах раз в которое будет сохранятся сборка По умолчанию 1800 чему равно 5 часам
  time:
    format: "yyyy-MM-dd-HH:mm:ss"
  blacklist: #Блеклист плагины
    - "server.jar"
  commands:
    enabled: true
    perm: "*"
  messages:
    cmdnt: "Команды выключены в config.yml!"
    noperms: "Нет прав!"
    save: "Началось принудительное сохранение сборки"
    saved: "Сохранил сборку за %ms милисекунд."
