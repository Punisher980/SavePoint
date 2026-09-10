# Savepoint 🎮

Um app Android minimalista para registrar, avaliar e descobrir jogos. A inspiração de produto vem do fluxo de diário social do Letterboxd, mas a identidade visual, nome, componentes e conteúdo são originais.

## O que já funciona

- Home com jogos populares, novidades de amigos, críticas, listas e diário
- Busca por título, gênero e estúdio
- Tela de detalhes do jogo
- Registro com nota de 0,5 a 5 estrelas
- Curtir, crítica, tags, plataforma, status, primeira jogatina e spoiler
- Perfil com atividade recente, estatísticas e histograma de notas
- Atividade de amigos, sua atividade e seguidores recebidos
- Dados de demonstração totalmente offline
- Avaliações e registros persistem no aparelho com armazenamento local
- Interface em Jetpack Compose, dark-first e responsiva

## Abrir no Android Studio

1. Abra a pasta do projeto no Android Studio.
2. Aguarde o Gradle Sync.
3. Rode em um aparelho/emulador Android 8.0+ (API 26+).

> O catálogo atual é local para o app abrir sem chave de API. A arquitetura foi deixada simples para conectar depois a RAWG ou IGDB.

## Identidade

- Nome: **Savepoint**
- Accent: verde neon `#38E683`
- Background: `#080C11`
- Tipografia: sans-serif limpa, alto contraste
- Package: `com.ju.savepoint`

## Próximos passos sugeridos

- Conta/login e backend (Supabase/Firebase)
- Catálogo real via RAWG/IGDB
- Seguidores, curtidas e comentários reais
- Listas customizadas persistentes
- Sincronização na nuvem
- Notificações
- Importar biblioteca Steam/PSN/Xbox
- Exportar perfil/diário


## Correção GitHub Actions v1.0.2

Os workflows não dependem mais do arquivo `./gradlew`. O GitHub Actions instala o Gradle 8.10.2 diretamente e localiza automaticamente o projeto, mesmo que ele esteja dentro da pasta `savepoint-app/`.

Para gerar o APK manualmente:
1. Abra **Actions** no GitHub.
2. Escolha **Savepoint - Gerar APK manualmente**.
3. Clique em **Run workflow**.
4. Ao finalizar, baixe o artifact `Savepoint-...-APK`.

**Importante:** ao criar o repositório, coloque `.github`, `app`, `gradle`, `build.gradle.kts`, `settings.gradle.kts` etc. na raiz do repositório. Não envie apenas o arquivo ZIP para o GitHub esperando que ele seja extraído.

## Correção GitHub Actions v1.0.3

- Android Gradle Plugin atualizado para **8.8.2**, combinação oficial com Gradle **8.10.2** e API 35.
- O workflow instala explicitamente Android SDK 35 e Build Tools 35.0.0.
- O build agora salva `build.log` mesmo quando falha.
- Quando houver erro, o Actions mostra um bloco **ERRO PRINCIPAL** com as linhas relevantes em vez de deixar apenas o fim do stacktrace.
