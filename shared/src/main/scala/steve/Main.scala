package steve

case class Build(
    base: Build.Base,
    commands: List[Command]
)

object Build:
    enum Base:
        case EmptyImage
        case ImageHash(hash: Hash)

enum Command:
    case Upsert(key: String)
    case Delete(key: String)

case class Hash(value: String)  

@main def main =
    println("Hello, world!")