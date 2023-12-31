Un viewModel est une classe séparées qui réside à coté du fragment et de l’activité. Il est responsable des données qui ont besoin d’etre affiché à l’écran.

tout le code concernant les données de l’application ou de la logique métier est déplacé de l’activité ou du fragment vers le viewModel et tout ce qui concerne le UI controller reste dans l’activité ou le fragment. Cela respecte le principe de conception “separation of concerns”; l’application est subdivisé en différentes classes de sorte à ce qu’elles s’occupent d’une seule preoccupation.

En utilisant ce type d’architecture d’application offre deux avantages :

- simplifier le code de l’activité et du fragment
- survit de l’application après les changements de configurations car le viewModel n’est pas détruit lorsque les changements de configuration, comme la rotation d’écran,

## Ajout de la dépendance viewModel au fichier *build.gradle* du module

La librairie viewModel fait partie de Android jetpack, pour l’utiliser il faut l’ajouter au fichier ***********build.gradle*********** du module

```jsx
dependencies{
		implementations("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
}
```

***ViewModel*** est une classe abstraite qui permet de transformer une simple classe en un viewModel. 

## Créer un objet viewModel

Pour lier un viewModel à une activité ou un fragment, il nous faut ajouter une propriété viewModel au code et l’initialiser avec un objet, et pour ca nous avons besoin d’un ********************view model provider******************** pour créer l’objet.

### utiliser un **ViewModelProvider** pour créer des viewModels

ViewModelProvider est une classe spéciale qui est chargée de fournir aux activités et aux fragments, les viewModels. Il s’assure qu’un nouvel objet viewModel est crée uniquement s’il n’en existe pas d’autres, cela permet que le meme viewModel soit utiliser, en sauvegardant son état, ses propriétés. Le fournisseur de viewModel maintient le viewModel aussi longtemps que le fragment ou l’activité reste en vie. Mais lorsqu’il sont détachés, le fournisseur de viewModel abandonne le viewModel et la prochaine fois qu’on lui demande un nouvel objet viewModel, il en créera un nouveau

```kotlin
class GameFragment: Fragment() {
		lateinit var viewModel: GameViewModel

		override fun createView(
				inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
		): View? {
				viewModel = ViewModelProvider(this).get(GameViewModel::class.java)
		}
}
```

### un viewModel factory crée des viewModel

Une alternative à la création des views model est de passée au forunisseur de view models un view model factory, qui est une classe séparées dont la seule tache est de créer et initialiser un view model. Cette approche permet d’eviter la création d’un viewModel en passant par le viewModel Provider mais plutot en utilisant le view model factory.

Le view model factory pouvant etre utilisé pour tout type de view model mais il est couramment utilisé pour ceux dont les constructeurs requièrent des arguments. Cela est du au fait que le view model provider ne peut pas passer des arguments à un constructeur par lui meme et qu’il a besoin du view model factory
