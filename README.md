# 📱 Hablemos Español - Android

Aplicação mobile para aprendizado de espanhol, focada em prática com frases, vocabulário e exercícios interativos.

---

## 🚀 Tecnologias

- Kotlin
- Android SDK
- Jetpack Compose
- Room (persistência local)
- Retrofit / API REST
- Gradle (Kotlin DSL)

---

## 📋 Pré-requisitos

Antes de rodar o projeto, você precisa ter instalado:

- Android Studio (recomendado versão mais recente)
- JDK 17+
- Emulador Android ou dispositivo físico

---

## ▶️ Como rodar a aplicação

1. Clone o repositório:

    git clone https://github.com/seu-usuario/hablemos-espanhol-android.git

2. Abra o projeto no Android Studio

3. Aguarde o Gradle sincronizar

4. Execute o app:

- Clique em **Run ▶️**
- Ou via terminal:

    ./gradlew installDebug

---

## 🔨 Como gerar build (APK)

### APK Debug

    ./gradlew assembleDebug

Arquivo gerado em:

    app/build/outputs/apk/debug/

---

### APK Release

    ./gradlew assembleRelease

Arquivo gerado em:

    app/build/outputs/apk/release/

> ⚠️ Para produção, configure assinatura (`signingConfig`)

---

## ⚙️ Configurações importantes

Verifique:

- URL da API (baseUrl)
- Configuração de ambiente (dev / prod)
- Permissões no AndroidManifest.xml

---

## 🧪 Rodando testes

    ./gradlew test

---

## 📁 Estrutura do projeto

    app/
     ├── data/          # Repositórios e fontes de dados
     ├── domain/        # Regras de negócio
     ├── ui/            # Telas (Compose)
     ├── core/          # Utilitários e configurações

---

## 🐛 Troubleshooting

### Gradle não sincroniza

    ./gradlew clean
    ./gradlew build

---

### Erros de dependência

- Verifique conexão com internet
- Limpe cache:

    ./gradlew cleanBuildCache

---

### App não instala

- Verifique se o dispositivo/emulador está ativo
- Habilite modo desenvolvedor no celular

---

## 📌 Roadmap (exemplo)

- [ ] Sistema de login
- [ ] Progresso do usuário
- [ ] Cache offline
- [ ] Melhorias de performance

---

## 🤝 Contribuição

1. Fork o projeto
2. Crie uma branch:

    git checkout -b minha-feature

3. Commit:

    git commit -m "feat: minha feature"

4. Push e abra um PR

---

## 📄 Licença

Este projeto é privado / uso interno.

---

## 👨‍💻 Autor

Desenvolvido por Tiago Silva 🚀
- [Github](https://github.com/cadnunsDimir)
- [Linkedin](https://www.linkedin.com/in/tiago-silva-do-nascimento/)
